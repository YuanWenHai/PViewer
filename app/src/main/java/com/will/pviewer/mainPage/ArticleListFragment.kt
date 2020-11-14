package com.will.pviewer.mainPage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.will.pviewer.MainActivity
import com.will.pviewer.R
import com.will.pviewer.articleDetail.ArticleDetailActivity
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.Series
import com.will.pviewer.databinding.FragmentArticleListBinding
import com.will.pviewer.mainPage.adapter.ArticleListAdapter
import com.will.pviewer.mainPage.adapter.ArticleListLoadStateAdapter
import com.will.pviewer.setting.LOG_TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

/**
 * created  by will on 2020/8/23 16:29
 */
 abstract class ArticleListFragment: Fragment() {

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

        val binding = FragmentArticleListBinding.inflate(inflater,container,false)
        init(binding)
        return binding.root
    }

    open fun useToolbar(): Boolean{
        return false
    }
    private fun init(binding: FragmentArticleListBinding){
        binding.fragmentArticleListToolbar.apply {
            if(useToolbar()){
                val parent = (requireActivity() as MainActivity)
                parent.setSupportActionBar(this)
                parent.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                setNavigationOnClickListener {
                    parent.onBackPressed()
                }
                visibility = View.VISIBLE
                title = getSeries().name
            }
        }
        val adapter = ArticleListAdapter{item: ArticleWithPictures -> onItemClick(item)}
        binding.fragmentArticleListRecycler.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ArticleListLoadStateAdapter(adapter),
            footer = ArticleListLoadStateAdapter(adapter)
        )
        binding.fragmentArticleListRefresh.setOnRefreshListener { adapter.refresh()}
        binding.fragmentArticleListRefresh.setColorSchemeResources(R.color.colorPrimary)
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            Log.d(LOG_TAG,"start collect articles with series: ${getSeries().name} ")
            getDataFlow().collectLatest {
                adapter.submitData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            adapter.loadStateFlow.collectLatest {
                binding.fragmentArticleListRefresh.isRefreshing = it.refresh is LoadState.Loading
                binding.fragmentArticleListEmptyMsg.isVisible = adapter.itemCount == 0
                //binding.fragmentArticleListRecycler.isVisible = adapter.itemCount != 0
            }
        }
    }

    private fun navigateToDetailActivity(articleWithPictures: ArticleWithPictures){
        startActivity(ArticleDetailActivity.buildIntent(requireActivity(),articleWithPictures))
    }


    abstract fun getSeries(): Series
    abstract fun getDataFlow(): Flow<PagingData<ArticleWithPictures>>
    open fun onItemClick(articleWithPictures: ArticleWithPictures){
        navigateToDetailActivity(articleWithPictures)
    }
}