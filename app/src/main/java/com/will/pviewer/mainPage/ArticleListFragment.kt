package com.will.pviewer.mainPage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.will.pviewer.R
import com.will.pviewer.base.BaseFragment
import com.will.pviewer.mainPage.adapter.ArticleListAdapter
import com.will.pviewer.mainPage.adapter.ArticleListLoadStateAdapter
import com.will.pviewer.data.*
import com.will.pviewer.databinding.FragmentArticleListBinding
import com.will.pviewer.mainPage.viewModel.AppViewModel
import com.will.pviewer.setting.LOG_TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * created  by will on 2020/8/23 16:29
 */
 abstract class ArticleListFragment: BaseFragment() {

   /* private val viewModel: ArticleListViewModel by viewModels{
        ArticleListViewModelFactory(
            ArticleWithPicturesRepository.getInstance(AppDatabase.getInstance(requireContext()).articleWithPicturesDao()),
            getSeries().alias)
    }*/


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)?.let {
            return it
        }
        val binding = FragmentArticleListBinding.inflate(inflater,container,false)
        init(binding)
        return binding.root
    }
    private fun init(binding: FragmentArticleListBinding){
        val adapter = ArticleListAdapter{item: ArticleWithPictures -> onItemClick(item)}
        binding.fragmentArticleListRecycler.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ArticleListLoadStateAdapter(adapter),
            footer = ArticleListLoadStateAdapter(adapter)
        )
        binding.fragmentArticleListRefresh.setOnRefreshListener { adapter.refresh()}
        binding.fragmentArticleListRefresh.setColorSchemeResources(R.color.colorPrimary)
        viewLifecycleOwner.lifecycleScope.launch {
            Log.d(LOG_TAG,"start collect articles with series: ${getSeries().name} ")
            getDataFlow().collectLatest {
                adapter.submitData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest {
                binding.fragmentArticleListRefresh.isRefreshing = it.refresh is LoadState.Loading
                binding.fragmentArticleListEmptyMsg.isVisible = adapter.itemCount == 0
                //binding.fragmentArticleListRecycler.isVisible = adapter.itemCount != 0
            }


        }
    }


    abstract fun getSeries(): Series
    abstract fun getDataFlow(): Flow<PagingData<ArticleWithPictures>>
    open fun onItemClick(articleWithPictures: ArticleWithPictures){

    }
}