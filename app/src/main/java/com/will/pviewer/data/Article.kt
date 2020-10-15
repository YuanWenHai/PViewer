package com.will.pviewer.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * created  by will on 2020/8/23 14:06
 */
@Entity(tableName = "articles")

data class Article(
    val uuid: String,
    val title: String,
    val author: String,
    val publishTime: String,
    val picCount: Int,
    val link: String,
    val series: String,
    val exist: Boolean
): Serializable {
    @PrimaryKey(autoGenerate = true) var id = 0

    constructor(article: Article,picCount: Int,exist: Boolean): this(article.uuid,article.title,article.author,article.publishTime,picCount,article.link,article.series,exist)
}