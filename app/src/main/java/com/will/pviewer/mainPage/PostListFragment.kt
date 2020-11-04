package com.will.pviewer.mainPage

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.Series
import com.will.pviewer.mainPage.viewModel.AppViewModel
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

    override fun onItemClick(articleWithPictures: ArticleWithPictures) {
        val action = PostListFragmentDirections.actionPostToDetail()
        findNavController().navigate(action)
    }
}