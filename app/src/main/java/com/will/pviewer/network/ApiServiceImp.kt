package com.will.pviewer.network

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.will.pviewer.setting.LOG_TAG
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.HTTP
import java.io.IOException
import java.lang.Exception
import java.net.ConnectException

/**
 * created  by will on 2020/10/10 18:33
 */
class ApiServiceImp private constructor(): ApiService {

    companion object{
        const val CONNECTION_ERROR = 666
        private const val BASE_URL = "http://10.4.1.76:8080/"
        //private const val BASE_URL = "http://192.168.50.68:8080/"
        private val instance = ApiServiceImp()
        fun get(): ApiService{
            return instance
        }
    }

    private val imp = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
        .build()
        .create(ApiService::class.java)



    override suspend fun getArticleList(
        series: String,
        page: Int,
        size: Int
    ): Response<ArticleQueryResponse> {
        return try{
            imp.getArticleList(series,page,size)
        }catch (i: IOException){
            i.printStackTrace()
            //Log.e(LOG_TAG,"met a connection error,may caused by the server")
            Response.error(CONNECTION_ERROR, ResponseBody.create(null,"met a connection error,may caused by the server"))
        }

    }

    override suspend fun getSeriesList(): Response<SeriesQueryResponse> {
        return try{
            imp.getSeriesList()
        }catch (i: IOException){
            i.printStackTrace()
            //Log.e(LOG_TAG,"met a connection error,may caused by the server")
            Response.error(CONNECTION_ERROR, ResponseBody.create(null,"met a connection error,may caused by the server"))
        }
    }
}