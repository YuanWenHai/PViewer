package com.will.pviewer.data

import androidx.room.Embedded
import androidx.room.Relation

/**
 * created  by will on 2020/8/23 15:05
 */
data class ArticleWithPictures(
    @Embedded val article: Article,
    @Relation(parentColumn = "id",entityColumn = "parentId")
    val pictureList: List<Picture>
){

}