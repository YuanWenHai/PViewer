package com.will.pviewer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * created  by will on 2020/8/23 14:15
 */
@Entity(tableName = "pictures")
data class Picture(
    @PrimaryKey val id: Int,
    val articleId: Int,
    val path: String,
    val size: Int,
    val name: String
) {

}