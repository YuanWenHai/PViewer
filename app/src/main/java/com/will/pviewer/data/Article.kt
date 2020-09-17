package com.will.pviewer.data

import androidx.room.Entity
import androidx.room.Ignore
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
    val category: String
): Serializable {
    @PrimaryKey(autoGenerate = true) var id = 0

}