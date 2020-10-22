package com.will.pviewer.mainPage

import androidx.navigation.fragment.findNavController
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.Series

/**
 * created  by will on 2020/10/20 17:52
 */
class SelfieListFragment: ArticleListFragment() {

    override fun getSeries(): Series {
        return Series.getSelfieSeries()
    }

    override fun onItemClick(articleWithPictures: ArticleWithPictures) {
        val action = SelfieListFragmentDirections.actionSelfieScreenToArticleDetail()
    }
}