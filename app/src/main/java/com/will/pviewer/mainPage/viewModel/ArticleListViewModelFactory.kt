package com.will.pviewer.mainPage.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.will.pviewer.data.ArticleWithPicturesRepository
import com.will.pviewer.network.ApiService
import com.will.pviewer.network.ApiServiceImp

/**
 * created  by will on 2020/8/25 15:48
 */
class ArticleListViewModelFactory(private val repos: ArticleWithPicturesRepository,private val series: String): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleListViewModel(repos, ApiServiceImp.get(),series) as T
    }
}