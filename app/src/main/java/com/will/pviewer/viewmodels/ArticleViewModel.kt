package com.will.pviewer.viewmodels

import androidx.lifecycle.ViewModel
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.ArticleWithPicturesRepository

/**
 * created  by will on 2020/8/23 15:57
 */
class ArticleViewModel( val articleWithPictures: ArticleWithPictures){
    private val article = articleWithPictures.article
    private val pictures = articleWithPictures.pictureList

    val name get() = article.name
    val author get() = article.author
    val picCount get() = article.picCount
    val publishTime get() = article.picCount
    val picList get() = pictures
}