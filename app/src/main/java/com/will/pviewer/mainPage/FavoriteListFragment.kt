package com.will.pviewer.mainPage

import androidx.fragment.app.activityViewModels
import androidx.paging.PagingData
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.Series
import com.will.pviewer.mainPage.viewModel.MainViewModel
import kotlinx.coroutines.flow.Flow

/**
 * created  by will on 2020/11/5 18:06
 */
class FavoriteListFragment: ArticleListFragment() {
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun getSeries(): Series {
        return Series.getFavoriteSeries()
    }

    override fun getDataFlow(): Flow<PagingData<ArticleWithPictures>> {
        return mainViewModel.favoriteFlow
    }

    override fun useToolbar(): Boolean {
        return true
    }
}