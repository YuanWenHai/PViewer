package com.will.pviewer.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * created  by will on 2020/8/23 14:15
 */
@Entity(tableName = "pictures")
data class Picture(
    val name: String,
    val url: String,
    val articleUuid: String,

    val path: String = "",
    val size: Int = 0
): Serializable {
    @PrimaryKey(autoGenerate = true) var id = 0
    constructor(picture: Picture,path: String,size: Int,exist: Boolean,articleUuid: String): this(picture.name,picture.url,articleUuid,path,size)
}