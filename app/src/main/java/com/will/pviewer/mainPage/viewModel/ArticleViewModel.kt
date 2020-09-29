package com.will.pviewer.mainPage.viewModel

import com.will.pviewer.data.ArticleWithPictures

/**
 * created  by will on 2020/8/23 15:57
 */
class ArticleViewModel( val articleWithPictures: ArticleWithPictures){
    private val article = articleWithPictures.article
    private val pictures = articleWithPictures.pictureList

    val name get() = article.title
    val author get() = article.author
    val picCount get() = article.picCount
    val publishTime get() = article.picCount
    val picList get() = pictures
}