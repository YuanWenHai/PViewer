package com.will.pviewer.network

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.will.pviewer.data.Series
import com.will.pviewer.setting.LOG_TAG
import okhttp3.Interceptor
import okhttp3.OkHttpClient
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
    suspend fun getArticleList(@Query("series") series: String = Series.Selfie, @Query("page") page: Int = 1, @Query("size")size: Int = 10): Response<ArticleQueryResponse>


    @GET("/series")
    suspend fun getSeriesList(): Response<SeriesQueryResponse>
}