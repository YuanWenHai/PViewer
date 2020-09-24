package com.will.pviewer.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * created  by will on 2020/9/17 10:39
 */
interface ApiService {

    @GET("/articles")
    suspend fun getArticleList(@Query("series") series: String = "",@Query("page") page: Int = 1,@Query("size")size: Int = 10): Response<ArticleQueryResponse>

    companion object{
        private const val BASE_URL = "http://10.4.1.106:8080/"
        //private const val BASE_URL = "http://192.168.50.68:8080/"

        private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        fun getApiService(): ApiService = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiService::class.java)
    }
}