package com.will.pviewer.articleDetail.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.will.pviewer.R
import com.will.pviewer.data.AppDatabase
import com.will.pviewer.data.Article
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.Picture
import com.will.pviewer.network.PictureDownloadCallback
import com.will.pviewer.network.PictureDownloader
import com.will.pviewer.setting.LOG_TAG
import java.io.File
import java.lang.IllegalArgumentException

/**
 * created  by will on 2020/10/14 12:43
 */
private const val SERVICE_NAME = "download_service"
private const val NOTIFICATION_ID = 668
class DownloadService: Service() {



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val articleWithPictures = getArticle(intent)
        val downloadDir = getDownloadDirectory(intent)
        startForeground(NOTIFICATION_ID,makeNotification(articleWithPictures.article.title,articleWithPictures.pictureList.size,0))
        download(articleWithPictures,downloadDir)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
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
                    getNotificationManager().notify(NOTIFICATION_ID,makeNotification(articleWithPictures.article.title,total,succeed))
                }

                override fun onError(picture: Picture) {

                }

                override fun onFinish(total: Int, succeed: Int,succeedList: List<Picture>) {
                    val article = Article(articleWithPictures.article,succeed,true)
                    AppDatabase.getInstance(this@DownloadService).apply {
                        articleDao().insertArticle(article)
                        pictureDao().insertPictures(succeedList)
                    }
                    val msg = "下载:${article.title} 到$fileDir ,共有图片${articleWithPictures.pictureList.size}张,下载成功$succeed 张"
                    Log.d(LOG_TAG,msg)
                    stopSelf()
                    //pushNotification(articleWithPictures.article.title,"download finished",0,0,notificationManager)
                }
            }).downloadPictures()
    }
    companion object{
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
}