package com.will.pviewer.paging

import androidx.paging.PagingSource
import com.will.pviewer.data.ArticleWithPictures

/**
 * created  by will on 2020/9/12 15:47
 */
class ArticleListPagingSource(): PagingSource<Int,ArticleWithPictures>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleWithPictures> {
        TODO("Not yet implemented")
    }
}