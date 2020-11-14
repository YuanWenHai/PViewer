package com.will.pviewer.mainPage

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.Series
import com.will.pviewer.mainPage.viewModel.ArticleListViewModel
import com.will.pviewer.network.ApiServiceImp
import kotlinx.coroutines.flow.Flow

/**
 * created  by will on 2020/10/20 18:16
 */
class  ArticleListFragmentImp private constructor(): ArticleListFragment() {

    private val viewModel: ArticleListViewModel by viewModels{
        object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ArticleListViewModel(ApiServiceImp.get(),getSeries().alias) as T
            }
        }
    }


    override fun useToolbar(): Boolean {
        return true
    }

    override fun getSeries(): Series {
        arguments?.let { bundle ->
            bundle.getSerializable(KEY_SERIES)?.let { series ->
                return series as Series
            }
        }
        throw IllegalArgumentException("no argument found,checking it")
    }

    override fun getDataFlow(): Flow<PagingData<ArticleWithPictures>> {
        return viewModel.articleFlow
    }


    companion object{
        private const val KEY_SERIES = "fragment_series"
        fun get(series: Series): ArticleListFragment{
            val fragment = ArticleListFragmentImp()
            fragment.arguments = Bundle().apply {
                putSerializable(KEY_SERIES,series)
            }
            return fragment
        }
    }
}