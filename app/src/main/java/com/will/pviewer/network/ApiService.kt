package com.will.pviewer.network

import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.Series
import retrofit2.Response
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

    @GET("article")
    suspend fun getArticle(@Query("id") articleId: Int): Response<ArticleResponse>
}