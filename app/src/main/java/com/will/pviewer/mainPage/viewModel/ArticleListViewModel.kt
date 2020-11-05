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
class ArticleListViewModel(apiService: ApiService,series: String): ViewModel() {

    val articleFlow = Pager(
            PagingConfig(pageSize = 10,enablePlaceholders = true,initialLoadSize = 10)
        ){
            ArticleListPagingSource(apiService,series)
        }.flow

}