package com.will.pviewer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.ArticleWithPicturesRepository

/**
 * created  by will on 2020/8/25 11:05
 */
class ArticleListViewModel(private val repository: ArticleWithPicturesRepository): ViewModel() {
    val articleWithPictures: LiveData<List<ArticleWithPictures>> = repository.getAllArticles()
    val articleWithPicturesPaging = Pager(
        PagingConfig(pageSize = 10,enablePlaceholders = true,maxSize = 200)
    ){
        repository.getAllArticlesInPaging()
    }.flow
}