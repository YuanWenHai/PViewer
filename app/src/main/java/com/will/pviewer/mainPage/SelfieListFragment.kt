package com.will.pviewer.mainPage

import com.will.pviewer.data.Series

/**
 * created  by will on 2020/10/20 17:52
 */
class SelfieListFragment: ArticleListFragment() {

    override fun getSeries(): Series {
        return Series.getSelfieSeries()
    }
}