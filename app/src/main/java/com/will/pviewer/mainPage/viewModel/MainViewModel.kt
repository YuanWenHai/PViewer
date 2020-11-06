package com.will.pviewer.mainPage.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.will.pviewer.data.ArticleWithPicturesRepository
import com.will.pviewer.data.Series
import com.will.pviewer.mainPage.navigation.NavigationItems
import com.will.pviewer.network.ApiService
import com.will.pviewer.network.SeriesResponse
import com.will.pviewer.paging.ArticleListPagingSource
import com.will.pviewer.setting.LOG_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * created  by will on 2020/11/4 17:43
 */
class MainViewModel(val apiService: ApiService,repository: ArticleWithPicturesRepository): ViewModel() {

    val selfieFlow = Pager(
        PagingConfig(pageSize = 10,enablePlaceholders = true,initialLoadSize = 10)
    ){
        ArticleListPagingSource(apiService, Series.getSelfieSeries().alias)
    }.flow

    val postFlow =  Pager(
        PagingConfig(pageSize = 10,enablePlaceholders = true,initialLoadSize = 10)
    ){
        ArticleListPagingSource(apiService, Series.getPostSeries().alias)
    }.flow

    val favoriteFlow = Pager(PagingConfig(pageSize = 20,enablePlaceholders = true,maxSize = 200)){
        repository.getAllArticlesInPaging()
    }.flow



    val toolbarTitle: MutableLiveData<String> = MutableLiveData()


    val isSeriesListLoading = MutableLiveData<Boolean>()
    val seriesList: MutableLiveData<List<Series>> = MutableLiveData()
    fun getSeries(){
        isSeriesListLoading.value = true
        viewModelScope.launch(Dispatchers.IO){
            val response = apiService.getSeriesList()
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    val result = response.body()?.list?.map { SeriesResponse.toSeries(it) }
                    seriesList.value = result
                }else{
                    Log.e(LOG_TAG,"error on getSeriesList() request,response code:${response.code()}")
                }
                isSeriesListLoading.value = false
            }
        }
    }

    private val currentSelectedNavigationItem: MutableLiveData<NavigationItems> = MutableLiveData(NavigationItems.Selfie)

    fun getCurrentSelectedNavigationItem(): LiveData<NavigationItems>{
        return currentSelectedNavigationItem
    }
    fun setCurrentSelectedNavigationItem(item: NavigationItems){
        currentSelectedNavigationItem.value?.let {
            if (item.index() != it.index()){
                currentSelectedNavigationItem.value = item
            }
        }
    }
}