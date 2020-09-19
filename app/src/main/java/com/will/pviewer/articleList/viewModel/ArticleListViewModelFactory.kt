package com.will.pviewer.articleList.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.will.pviewer.data.ArticleWithPicturesRepository
import com.will.pviewer.network.ApiService

/**
 * created  by will on 2020/8/25 15:48
 */
class ArticleListViewModelFactory(private val repos: ArticleWithPicturesRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleListViewModel(repos, ApiService.getApiService()) as T
    }
}