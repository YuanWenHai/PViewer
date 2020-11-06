package com.will.pviewer.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * created  by will on 2020/8/23 14:15
 */
@Entity(tableName = "pictures")
data class Picture(

    @PrimaryKey
    val id: Int,
    val name: String,
    val url: String,
    val articleId: Int,

    val path: String = "",
    val size: Int = 0,
    val exist: Boolean = false
): Serializable {
    constructor(picture: Picture,path: String,size: Int,exist: Boolean,articleId: Int): this(picture.id,picture.name,picture.url,articleId,path,size,exist)
}