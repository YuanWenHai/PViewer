package com.will.pviewer.mainPage

import com.will.pviewer.data.Series

/**
 * created  by will on 2020/10/20 17:53
 */
class PostListFragment: ArticleListFragment() {

    override fun getSeries(): Series {
        return Series.getPostSeries()
    }
}