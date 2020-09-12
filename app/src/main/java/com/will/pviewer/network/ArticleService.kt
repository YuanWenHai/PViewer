package com.will.pviewer.network

import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

/**
 * created  by will on 2020/9/12 15:51
 */
class ArticleService private constructor() {
    private val okClient = OkHttpClient()

    fun getArticleList(page: Int,size: Int){
        val request = Request.Builder().url("http://localhost:8080/").build()
        val response = okClient.newCall(request).execute()
        if(response.code == 200){
            if(response.body != null){
                val json = JSONObject(response.body.toString())
                Log.e("title",json.getString("title"))
            }
        }


    }

    companion object{
        private var instance: ArticleService? = null

        fun getInstance(context: Context): ArticleService{
            return instance ?: synchronized(this){
                instance ?: ArticleService().also { instance = it}
            }
        }
    }
}