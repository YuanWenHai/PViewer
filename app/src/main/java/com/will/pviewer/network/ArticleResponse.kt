package com.will.pviewer.network

import com.squareup.moshi.Json
import com.will.pviewer.data.Article
import com.will.pviewer.data.ArticleWithPictures

/**
 * created  by will on 2020/9/17 10:32
 */
data class ArticleResponse(
    @Json(name = "uuid")
    val uuid: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "author")
    val author: String,
    @Json(name = "publishTime")
    val publishTime: String,
    @Json(name = "picCount")
    val picCount: Int,
    @Json(name = "link")
    val link: String,
    @Json(name = "series")
    val series: String,
    @Json(name = "pictureList")
    val pictureList: List<PictureResponse>
) {

    fun toArticleWithPictures(): ArticleWithPictures{
        val article = Article(uuid,title,author,publishTime,picCount,link, series,false)
        val pictures = pictureList.map { it.toPicture(article) }
        return ArticleWithPictures(article,pictures)
    }
    companion object{
        fun toArticleWithPicturesList(list: List<ArticleResponse>): List<ArticleWithPictures>{
            return list.map { it.toArticleWithPictures() }
        }
    }
}