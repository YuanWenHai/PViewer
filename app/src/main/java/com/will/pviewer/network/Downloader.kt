package com.will.pviewer.network

import android.util.Log
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.file.FileHelper
import com.will.pviewer.setting.LOG_TAG
import okhttp3.*
import java.io.IOException

/**
 * created  by will on 2020/9/20 18:05
 */
class Downloader {
    private val okClient = OkHttpClient.Builder().build()

    fun download(articleWithPictures: ArticleWithPictures){
        val pictures = articleWithPictures.pictureList
        pictures.forEach{
            if(it.exist){
                Log.w(LOG_TAG,"skip download picture of article:${articleWithPictures.article.title} the target picture already exists")
            }else{
                val request = Request.Builder().url(it.url).build()
                okClient.newCall(request).enqueue(object: Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        Log.w(LOG_TAG,"picture of article:${articleWithPictures.article.title} download failure,url is:${it.url}")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if(response.isSuccessful && response.body() != null){
                            FileHelper
                            response.body()?.byteStream()
                        }
                    }
                })
            }

        }

    }
}