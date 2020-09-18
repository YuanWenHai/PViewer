package com.will.pviewer.paging

import android.util.Log
import androidx.paging.PagingSource
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.network.ApiService
import com.will.pviewer.network.ArticleResponse
import com.will.pviewer.setting.LOG_TAG
import okhttp3.Response
import java.lang.Exception

/**
 * created  by will on 2020/9/12 15:47
 */
class ArticleListPagingSource(private val apiService: ApiService): PagingSource<Int,ArticleWithPictures>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleWithPictures> {
        val pageCount = params.key ?: 1
        val pageSize = params.loadSize
        val response = apiService.getArticleList(pageSize = pageSize,pageCount = pageCount)
        Log.d(LOG_TAG,"on pagingSource load -> pageCount:$pageCount   pageSize:$pageSize paramsKey: ${params.key}")
        if(response.code() != 200){
            val errorMsg = "network error, response code is: ${response.code()} \nerror response body string is: ${response.errorBody()?.string()}"
            val e = Exception(errorMsg)
            Log.w(LOG_TAG,errorMsg)
            return LoadResult.Error(e)
        }
        val responseList = mutableListOf<ArticleResponse>()
        val list = response.body()?.articleList ?: emptyList()
        responseList.addAll(list)

        val data = ArticleResponse.toArticleWithPicturesList(responseList)
        return LoadResult.Page(data = data,prevKey = null,nextKey = if (response.body()?.hasNextPage == true) pageCount.plus(1) else null)
    }
}