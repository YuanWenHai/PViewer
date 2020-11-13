package com.will.pviewer.mainPage

import androidx.fragment.app.activityViewModels
import com.will.pviewer.data.Series
import com.will.pviewer.mainPage.viewModel.MainViewModel

/**
 * created  by will on 2020/10/20 17:53
 */
class PostListFragment: ArticleListFragment() {
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun getSeries(): Series {
        return Series.getPostSeries()
    }

    override fun getDataFlow() = mainViewModel.postFlow


}