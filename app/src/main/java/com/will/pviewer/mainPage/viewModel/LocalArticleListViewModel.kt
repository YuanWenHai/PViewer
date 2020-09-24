package com.will.pviewer.mainPage.viewModel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.will.pviewer.data.ArticleWithPicturesRepository

/**
 * created  by will on 2020/9/11 15:14
 */
class LocalArticleListViewModel(private val repos: ArticleWithPicturesRepository): ViewModel() {
    init {
        val articles = Pager(
            PagingConfig(pageSize = 60,enablePlaceholders = true,maxSize = 200)
        ){
            repos.getAllArticlesInPaging()
        }.flow
    }

}