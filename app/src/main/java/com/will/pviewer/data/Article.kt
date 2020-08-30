package com.will.pviewer.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * created  by will on 2020/8/23 14:06
 */
@Entity(tableName = "articles")

data class Article @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val author: String,
    val publishTime: String,
    val picCount: Int,
    val link: String,
    @Ignore val pics: List<Picture>? = null
) {}