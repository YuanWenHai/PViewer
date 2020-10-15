package com.will.pviewer.network

import android.util.Log
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.Picture
import com.will.pviewer.file.FileHelper
import com.will.pviewer.setting.LOG_TAG
import okhttp3.*
import java.io.File
import java.io.IOException

/**
 * created  by will on 2020/9/20 18:05
 * 图片列表下载器，每次下载需初始化一对象
 */
class PictureDownloader(private val destDir: File, private val articleWithPictures: ArticleWithPictures, private val callback: PictureDownloadCallback) {
    private val okClient = OkHttpClient.Builder().build()
    @Volatile private var finished = 0
    @Volatile private var succeed = 0
    private var succeedList: MutableList<Picture> = mutableListOf()
    private val pictures = articleWithPictures.pictureList

    fun downloadPictures(){
        pictures.forEach{ picture ->
            val request = Request.Builder().url(picture.url).build()
            okClient.newCall(request).enqueue(object: Callback{
                override fun onFailure(call: Call, e: IOException) {
                    Log.w(LOG_TAG,"picture: ${picture.url} download failure")
                    callback.onError(picture)
                    checkIfFinished()
                }

                override fun onResponse(call: Call, response: Response) {
                    if(response.body() != null){
                        //val fileSuffix = picture.url.subSequence(picture.url.lastIndexOf("."),picture.url.length).toString()
                        val filePath = File(destDir,picture.name)
                        FileHelper.writeStreamToFile(response.body()!!.byteStream(),filePath)
                        val resultPicture = Picture(picture,filePath.path,
                            response.body()!!.byteStream().readBytes().size,true,articleWithPictures.article.uuid)
                        succeed++
                        callback.onResult(pictures.size,succeed,resultPicture)
                        collectSucceed(resultPicture)
                    }else{
                        callback.onError(picture)
                        Log.w(LOG_TAG,"picture: ${picture.url} download failure,response body is null")
                    }
                    checkIfFinished()
                }
            })
        }
        }

    private fun collectSucceed(picture: Picture){
        synchronized(this){
            succeedList.add(picture)
        }
    }
    private fun checkIfFinished(){
        finished++
        if(finished == pictures.size){
            callback.onFinish(finished,succeed,succeedList)
        }
    }
}
interface PictureDownloadCallback{
    fun onResult(total: Int,succeed: Int,picture: Picture)
    fun onError(picture: Picture)
    fun onFinish(total: Int,succeed: Int,succeedList: List<Picture>)
}