package com.will.pviewer.mainPage

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.Series
import com.will.pviewer.mainPage.viewModel.AppViewModel

/**
 * created  by will on 2020/10/20 17:53
 */
class PostListFragment: ArticleListFragment() {
    private val appViewModel: AppViewModel by activityViewModels()

    override fun getSeries(): Series {
        return Series.getPostSeries()
    }

    override fun getDataFlow() = appViewModel.postFlow

    override fun onItemClick(articleWithPictures: ArticleWithPictures) {
        val action = PostListFragmentDirections.actionPostToDetail()
        appViewModel.currentDisplayingArticle.value = articleWithPictures
        findNavController().navigate(action)
    }
}