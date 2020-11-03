package com.will.pviewer.mainPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.Series
import com.will.pviewer.mainPage.viewModel.AppViewModel

/**
 * created  by will on 2020/10/20 17:52
 */
class SelfieListFragment: ArticleListFragment() {
    private val appViewModel: AppViewModel by activityViewModels()
    override fun getSeries(): Series {
        return Series.getSelfieSeries()
    }


    override fun getDataFlow() = appViewModel.selfieFlow

    override fun onItemClick(articleWithPictures: ArticleWithPictures) {
        val action = SelfieListFragmentDirections.actionSelfieToDetail()
        appViewModel.currentDisplayingArticle.value = articleWithPictures

        findNavController().navigate(action)
    }
}