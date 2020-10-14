package com.will.pviewer.articleDetail.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.app.NotificationCompat
import com.will.pviewer.MainActivity
import com.will.pviewer.R
import com.will.pviewer.data.AppDatabase
import com.will.pviewer.data.Article
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.Picture
import com.will.pviewer.network.PictureDownloadCallback
import com.will.pviewer.network.PictureDownloader
import com.will.pviewer.setting.LOG_TAG
import java.io.File
import java.io.InterruptedIOException

/**
 * created  by will on 2020/10/14 12:43
 */
private const val SERVICE_NAME = "download_service"
private const val NOTIFICATION_ID = 668
const val ARTICLE_DATA = "article_data"
class DownloadService: IntentService(SERVICE_NAME) {


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val pendingIntent: PendingIntent = Intent(this, MainActivity::class.java).let {
            PendingIntent.getActivity(this,0,it,0)
        }
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, SERVICE_NAME)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Article Title")
            .setContentText("Article Content")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setProgress(100,30,false)
            .build()

        startForeground(NOTIFICATION_ID,notification)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        intent?.let {
            it.getSerializableExtra(ARTICLE_DATA)
        }
        val articleWithPictures = intent?.
        pushNotification()

    }
    private fun pushNotification(title: String,content: String,max: Int,progress: Int,manager: NotificationManager){
        val notification =  NotificationCompat.Builder(this, SERVICE_NAME)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setProgress(max,progress,false)
            .build()
        manager.notify(NOTIFICATION_ID,notification)
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
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun download(articleWithPictures: ArticleWithPictures){
        val root = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val fileDir = File(root,articleWithPictures.article.uuid)
        fileDir.mkdirs()
        PictureDownloader(fileDir,articleWithPictures,
            object: PictureDownloadCallback {
                override fun onResult(picture: Picture) {

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
                }
            }).downloadPictures()
    }
}