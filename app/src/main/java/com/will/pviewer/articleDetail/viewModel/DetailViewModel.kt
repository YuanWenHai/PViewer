package com.will.pviewer.articleDetail.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.will.pviewer.articleDetail.service.DownloadService
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.ArticleWithPicturesDao
import com.will.pviewer.network.ApiService
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

/**
 * created  by will on 2020/9/18 18:02
 */
class DetailViewModel(private val articleId: Int,private val dao: ArticleWithPicturesDao,private val api: ApiService): ViewModel() {
    val article = MutableLiveData<ArticleWithPictures>()
    val currentIndex = MutableLiveData(0)
    val downloadBinder: MutableLiveData<DownloadService.DownloadBinder> = MutableLiveData()
    val articleExistOnLocal = MutableLiveData<Boolean>()

    fun getArticle(){

        viewModelScope.launch(IO) {
            val localResult = dao.getArticleWIthPicturesById(articleId)
            var result: ArticleWithPictures
            if(localResult == null){
                val remoteResult = api.getArticle(articleId)
                if(remoteResult.isSuccessful){
                    remoteResult.body()?.let {
                        result = it.toArticleWithPictures()
                        withContext(Main){
                            article.value = result
                        }
                    }
                }
            }else{
                result = localResult
                withContext(Main){
                    article.value = result
                    articleExistOnLocal.value = result.article.exist
                }
            }

        }
    }
}