package com.will.pviewer.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * created  by will on 2020/8/23 14:06
 */
@Entity(tableName = "articles")

data class Article(
    @PrimaryKey
    val id: Int,
    val title: String,
    val author: String,
    val publishTime: String,
    val picCount: Int,
    val link: String,
    val series: String,
    val exist: Boolean
): Serializable {

    constructor(article: Article,picCount: Int,exist: Boolean): this(article.id,article.title,article.author,article.publishTime,picCount,article.link,article.series,exist)
}