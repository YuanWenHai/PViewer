package com.will.pviewer.data

import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable

/**
 * created  by will on 2020/8/23 15:05
 */
data class ArticleWithPictures(
    @Embedded val article: Article,
    @Relation(parentColumn = "uuid",entityColumn = "articleUuid")
    val pictureList: List<Picture>
): Serializable{

}