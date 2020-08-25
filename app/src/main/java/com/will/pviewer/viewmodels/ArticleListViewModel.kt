package com.will.pviewer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.ArticleWithPicturesRepository

/**
 * created  by will on 2020/8/25 11:05
 */
class ArticleListViewModel(private val repository: ArticleWithPicturesRepository): ViewModel() {
    val articleWithPictures: LiveData<List<ArticleWithPictures>> = repository.getAllArticles()
}