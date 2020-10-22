package com.will.pviewer.mainPage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.will.pviewer.R
import com.will.pviewer.mainPage.adapter.ArticleListAdapter
import com.will.pviewer.mainPage.adapter.ArticleListLoadStateAdapter
import com.will.pviewer.data.*
import com.will.pviewer.databinding.FragmentArticleListBinding
import com.will.pviewer.setting.LOG_TAG
import com.will.pviewer.mainPage.viewModel.ArticleListViewModel
import com.will.pviewer.mainPage.viewModel.ArticleListViewModelFactory
import kotlinx.coroutines.flow.collectLatest

/**
 * created  by will on 2020/8/23 16:29
 */
 abstract class ArticleListFragment: Fragment() {


    private val viewModel: ArticleListViewModel by viewModels{
        ArticleListViewModelFactory(
            ArticleWithPicturesRepository.getInstance(AppDatabase.getInstance(requireContext()).articleWithPicturesDao()),
            getSeries().alias)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            Log.d(LOG_TAG,"start collect articles with series: ${getSeries().name} ")
            viewModel.articles.collectLatest {
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
    open fun onItemClick(articleWithPictures: ArticleWithPictures){
        
    }
}