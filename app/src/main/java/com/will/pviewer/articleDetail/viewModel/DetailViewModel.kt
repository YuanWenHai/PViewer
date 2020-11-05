package com.will.pviewer.articleDetail.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.will.pviewer.data.ArticleWithPictures

/**
 * created  by will on 2020/9/18 18:02
 */
class DetailViewModel(articleWithPictures: ArticleWithPictures): ViewModel() {
    private val article = MutableLiveData<ArticleWithPictures>(articleWithPictures)
    val currentIndex = MutableLiveData(0)


    fun getArticle(): LiveData<ArticleWithPictures>{
        return article
    }
}