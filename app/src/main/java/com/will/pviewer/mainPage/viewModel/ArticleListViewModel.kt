package com.will.pviewer.mainPage.viewModel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.will.pviewer.data.ArticleWithPicturesRepository
import com.will.pviewer.network.ApiService
import com.will.pviewer.paging.ArticleListPagingSource

/**
 * created  by will on 2020/8/25 11:05
 */
class ArticleListViewModel(private val repository: ArticleWithPicturesRepository,private val apiService: ApiService): ViewModel() {
    val articles = Pager(
        PagingConfig(pageSize = 10,enablePlaceholders = true,initialLoadSize = 10)
    ){
        ArticleListPagingSource(apiService)
    }.flow

    val localArticles = Pager(PagingConfig(pageSize = 20,enablePlaceholders = true,maxSize = 200)){
        repository.getAllArticlesInPaging()
    }.flow
}