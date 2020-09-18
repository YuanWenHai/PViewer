package com.will.pviewer.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * created  by will on 2020/8/23 14:15
 */
@Entity(tableName = "pictures")
data class Picture(
    val uuid: String,
    val url: String,
    val articleUuid: String,

    var path: String = "",
    var size: Int = 0,
    var exist: Boolean = false
): Serializable {
    @PrimaryKey(autoGenerate = true) var id = 0
}