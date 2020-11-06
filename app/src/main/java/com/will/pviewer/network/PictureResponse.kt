package com.will.pviewer.network

import com.squareup.moshi.Json
import com.will.pviewer.data.Article
import com.will.pviewer.data.Picture

/**
 * created  by will on 2020/9/17 10:36
 */
data class PictureResponse(

    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
) {
    fun toPicture(article: Article): Picture{
        return Picture(id = id,name = name,url = url,articleId = article.id)
    }
}