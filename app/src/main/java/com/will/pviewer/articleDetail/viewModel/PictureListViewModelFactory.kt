package com.will.pviewer.articleDetail.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.will.pviewer.data.ArticleWithPictures

/**
 * created  by will on 2020/9/19 9:26
 */
class PictureListViewModelFactory(private val article: ArticleWithPictures): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PictureListViewModel(article) as T
    }
}