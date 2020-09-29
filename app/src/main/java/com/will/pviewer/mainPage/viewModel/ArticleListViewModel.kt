package com.will.pviewer.mainPage.viewModel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.ArticleWithPicturesRepository
import com.will.pviewer.data.Series
import com.will.pviewer.network.ApiService
import com.will.pviewer.paging.ArticleListPagingSource
import org.intellij.lang.annotations.Flow

/**
 * created  by will on 2020/8/25 11:05
 */
class ArticleListViewModel(private val repository: ArticleWithPicturesRepository,private val apiService: ApiService,private val series: String): ViewModel() {

    val articles = when(series){
        Series.Local -> Pager(PagingConfig(pageSize = 20,enablePlaceholders = true,maxSize = 200)){
                repository.getAllArticlesInPaging()
            }.flow

        else -> Pager(
            PagingConfig(pageSize = 10,enablePlaceholders = true,initialLoadSize = 10)
        ){
            ArticleListPagingSource(apiService,series)
        }.flow
    }
}