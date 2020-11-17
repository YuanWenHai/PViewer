package com.will.pviewer.mainPage.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.will.pviewer.sp.SPUtil
import org.json.JSONArray
import java.util.*

/**
 * created  by will on 2020/11/17 11:04
 */
class SettingViewModel(): ViewModel() {
    private val currentServer: MutableLiveData<String> = MutableLiveData()
    private val otherServer: MutableLiveData<String> = MutableLiveData()


    fun refreshServer(context: Context){
        currentServer.value = SPUtil.getCurrentServer(context)
        otherServer.value = SPUtil.getOtherServer(context)
    }


    fun getCurrentServer(): LiveData<String>{
        return currentServer
    }
    fun getOtherServer(): LiveData<String>{
        return otherServer
    }
    fun switchServer(context: Context){
        SPUtil.switchServer(context)
        refreshServer(context)
    }



}