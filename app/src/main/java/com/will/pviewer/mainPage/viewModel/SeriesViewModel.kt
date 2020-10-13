package com.will.pviewer.mainPage.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.will.pviewer.data.Series
import com.will.pviewer.network.ApiService
import com.will.pviewer.network.SeriesResponse
import com.will.pviewer.setting.LOG_TAG
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SeriesViewModel(private val api: ApiService): ViewModel() {
    private val series: MutableLiveData<List<Series>> = MutableLiveData()

    private val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getSeries(): LiveData<List<Series>>{
        isLoading.value = true
        viewModelScope.launch(IO){
            val response = api.getSeriesList()
            withContext(Main){
                if(response.isSuccessful){
                    val seriesList = response.body()?.list?.map { SeriesResponse.toSeries(it) }
                    series.value = seriesList
                }else{
                    Log.e(LOG_TAG,"error on getSeriesList() request,response code:${response.code()}")
                }
                isLoading.value = false
            }
        }
        return series
    }
    fun isLoading(): LiveData<Boolean> = isLoading
}