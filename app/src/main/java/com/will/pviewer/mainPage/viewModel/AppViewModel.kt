package com.will.pviewer.mainPage.viewModel

import android.graphics.pdf.PdfDocument
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.will.pviewer.data.ArticleWithPicturesRepository
import com.will.pviewer.data.Series
import com.will.pviewer.network.ApiService
import com.will.pviewer.paging.ArticleListPagingSource

class AppViewModel(apiService: ApiService,repository: ArticleWithPicturesRepository): ViewModel() {

    val selfie = Pager(
        PagingConfig(pageSize = 10,enablePlaceholders = true,initialLoadSize = 10)
    ){
        ArticleListPagingSource(apiService,Series.getSelfieSeries().alias)
    }.flow

    val post =  Pager(
        PagingConfig(pageSize = 10,enablePlaceholders = true,initialLoadSize = 10)
    ){
        ArticleListPagingSource(apiService,Series.getPostSeries().alias)
    }.flow

    val favorite = Pager(PagingConfig(pageSize = 20,enablePlaceholders = true,maxSize = 200)){
        repository.getAllArticlesInPaging()
    }.flow



    val series: MutableLiveData<List<Series>> = MutableLiveData()


}