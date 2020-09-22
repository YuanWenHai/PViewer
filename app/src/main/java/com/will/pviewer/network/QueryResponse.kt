package com.will.pviewer.network

import com.squareup.moshi.Json
import com.will.pviewer.data.ArticleWithPictures

/**
 * created  by will on 2020/9/16 10:31
 */
data class  QueryResponse(
    @Json(name = "total")
    val total: Int,
    @Json(name = "totalPage")
    val totalPage: Int,
    @Json(name = "pageSize")
    val pageSize: Int,
    @Json(name = "pageCount")
    val pageCount: Int,
    @Json(name = "articleList")
    val articleList: List<ArticleResponse>,
    @Json(name = "hasNextPage")
    val hasNextPage: Boolean
    ) {

}