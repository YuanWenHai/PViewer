package com.will.pviewer.mainPage

import androidx.paging.PagingData
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.Series
import com.will.pviewer.mainPage.viewModel.AppViewModel
import kotlinx.coroutines.flow.Flow

/**
 * created  by will on 2020/10/20 18:16
 */
class ArticleListFragmentImp: ArticleListFragment() {

    override fun getSeries(): Series {
        TODO("Not yet implemented")
    }

    override fun getDataFlow(): Flow<PagingData<ArticleWithPictures>> {
        TODO("Not yet implemented")
    }
}