package com.will.pviewer.articleDetail.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import android.util.SparseArray
import androidx.core.app.NotificationCompat
import androidx.core.util.set
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.will.pviewer.R
import com.will.pviewer.data.AppDatabase
import com.will.pviewer.data.Article
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.Picture
import com.will.pviewer.extension.syncAdd
import com.will.pviewer.extension.syncRemove
import com.will.pviewer.network.PictureDownloadCallback
import com.will.pviewer.network.PictureDownloader
import com.will.pviewer.setting.LOG_TAG
import java.io.File
import java.lang.IllegalArgumentException
import java.util.*
import java.util.concurrent.SynchronousQueue

/**
 * created  by will on 2020/10/14 12:43
 */
private const val SERVICE_NAME = "download_service"
private const val NOTIFICATION_ID = 668
class DownloadService: Service() {

    private val downloadQueue: Queue<ArticleWithPictures> = LinkedList()
    private val downloadingList: MutableList<ArticleWithPictures> = mutableListOf()
    private val downloadStatus: SparseArray<MutableLiveData<Int>> = SparseArray()
    private val maxConcurrent = 5
    private val workThread = HandlerThread(SERVICE_NAME)
    private val mainHandler = Handler()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        init()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun init(){
        val dir = getDownloadRootDir()
        if (dir == null){
            Log.e(LOG_TAG,"download directory is null, cancel thread start")
            return
        }
        workThread.start()
        val workHandler = Handler(workThread.looper)
        val runnable = object: Runnable {
            override fun run() {
                if(downloadingList.size <= maxConcurrent && downloadQueue.peek() != null){
                    val article = downloadQueue.remove()
                    downloadingList.syncAdd(article)
                    download(article,dir)
                }
                workHandler.postDelayed(this,100)
            }
        }
        workHandler.post(runnable)
    }

    private fun getDownloadRootDir(): File?{
        val externalAvailable = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        if(externalAvailable){
             return getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        }
        Log.e(LOG_TAG, "externalAvailable is false,cannot get app external storage directory")
        return null
    }


    fun download(article: ArticleWithPictures): LiveData<Int>?{
        for (d in downloadingList){
            if(d.article.id == article.article.id){
                return null
            }
        }

        downloadQueue.offer(article)
        getNotificationManager().notify(article.article.id,makeNotification(article.article.title,article.pictureList.size,0))
        val status = MutableLiveData(STATUS_DOWNLOADING)
        downloadStatus[article.article.id] = status
        return status
    }


    override fun onBind(intent: Intent?): IBinder? {
        return DownloadBinder()
    }

    private fun getNotificationManager(): NotificationManager{
        return getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    }
    private fun makeNotification(title: String,max: Int,progress: Int): Notification{
        return NotificationCompat.Builder(this, SERVICE_NAME)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText("$progress/$max")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setProgress(max,progress,false)
            .build()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText = "A service that running for download article picture"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(SERVICE_NAME, SERVICE_NAME, importance).apply {
                description = descriptionText
            }
            getNotificationManager().createNotificationChannel(channel)
        }
    }

    private fun download(articleWithPictures: ArticleWithPictures,root: File){
        val fileDir = File(root,articleWithPictures.article.id.toString())
        fileDir.mkdirs()

        PictureDownloader(fileDir,articleWithPictures,
            object: PictureDownloadCallback {

                override fun onResult(total: Int, succeed: Int, picture: Picture) {
                    getNotificationManager().notify(articleWithPictures.article.id,makeNotification(articleWithPictures.article.title,total,succeed))
                }

                override fun onError(picture: Picture) {
                    downloadingList.syncRemove(articleWithPictures)
                    changeDownloadStatus(articleWithPictures.article.id, STATUS_ERROR)
                }

                override fun onFinish(total: Int, succeed: Int,succeedList: List<Picture>) {
                    val article = Article(articleWithPictures.article,succeed,true)
                    AppDatabase.getInstance(this@DownloadService).apply {
                        articleDao().insertArticle(article)
                        pictureDao().insertPictures(succeedList)
                    }
                    val msg = "下载:${article.title} 到$fileDir ,共有图片$total 张,下载成功$succeed 张"
                    Log.d(LOG_TAG,msg)
                    downloadingList.syncRemove(articleWithPictures)
                    changeDownloadStatus(articleWithPictures.article.id, STATUS_DOWNLOADED)
                    getNotificationManager().cancel(articleWithPictures.article.id)
                }
            }).downloadPictures()
    }

    private fun changeDownloadStatus(id: Int,status: Int){
        mainHandler.post{
            downloadStatus[id].value = status
        }
    }
    companion object{
        const val STATUS_DOWNLOADING = 1001
        const val STATUS_ERROR = 1002
        const val STATUS_DOWNLOADED = 1003
        private const val DATA_ARTICLE = "data_article"
        private const val DATA_DIR = "data_dir"
        fun buildIntent(context: Context,articleWithPictures: ArticleWithPictures,downloadDir: File): Intent{
            return Intent(context,DownloadService::class.java).apply {
                putExtra(DATA_ARTICLE,articleWithPictures)
                putExtra(DATA_DIR,downloadDir)
            }
        }
        fun getArticle(intent: Intent?): ArticleWithPictures{
            intent?.let { param ->
                param.getSerializableExtra(DATA_ARTICLE)?.let {
                    return it as ArticleWithPictures
                }
            }
            throw IllegalArgumentException("no data found in intent,must build intent from buildIntent()!!!")
        }
        fun getDownloadDirectory(intent: Intent?): File{
            intent?.let { param ->
                param.getSerializableExtra(DATA_DIR)?.let {
                    return it as File
                }
            }
            throw IllegalArgumentException("no data found in intent,must build intent from buildIntent()!!!")
        }
    }

    inner class DownloadBinder: Binder(){
        public fun getService(): DownloadService{
            return this@DownloadService
        }
    }
}