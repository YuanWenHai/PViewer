package com.will.pviewer.network

import com.will.pviewer.data.ArticleWithPictures
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response

/**
 * created  by will on 2020/9/12 15:51
 */
class ArticleService private constructor() {
    /*private val okClient = OkHttpClient()

     fun getArticleList(page: Int,size: Int): QueryListResult<ArticleWithPictures>?{
        val request = Request.Builder().url("http://localhost:8080/").build()
        val response = okClient.newCall(request).execute()
        if(response.code == 200) {
            if (response.body != null) {
                return DataUtil.convertQueryResult2ArticleList(response.body.toString())
            }
        }

        return null

    }*/
    /*suspend fun getArticleList(page: Int,size: Int): Response<QueryResponse>{

    }*/

    companion object{
        private var instance: ArticleService? = null

        fun getInstance(): ArticleService{
            return instance ?: synchronized(this){
                instance ?: ArticleService().also { instance = it}
            }
        }
    }
}