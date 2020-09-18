package com.will.pviewer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.will.pviewer.data.ArticleWithPictures

/**
 * created  by will on 2020/9/18 18:02
 */
class PictureListViewModel(private val articleWithPictures: ArticleWithPictures): ViewModel() {
    val article = MutableLiveData<ArticleWithPictures>(articleWithPictures)
}