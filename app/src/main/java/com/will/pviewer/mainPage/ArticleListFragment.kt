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
class ArticleListFragment private  constructor(): Fragment() {


    private val viewModel: ArticleListViewModel by viewModels{
        ArticleListViewModelFactory(
            ArticleWithPicturesRepository.getInstance(AppDatabase.getInstance(requireContext()).articleWithPicturesDao()),
            getSeries(this).alias)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentArticleListBinding.inflate(inflater,container,false)

        initAdapter(binding)

        return binding.root
    }
    private fun initAdapter(binding: FragmentArticleListBinding){
        val adapter = ArticleListAdapter()
        binding.fragmentArticleListRecycler.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ArticleListLoadStateAdapter(adapter),
            footer = ArticleListLoadStateAdapter(adapter)
        )
        binding.fragmentArticleListRefresh.setOnRefreshListener { adapter.refresh()}

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            Log.d(LOG_TAG,"start collect articles with series: ${getSeries(this@ArticleListFragment).name} ")
            viewModel.articles.collectLatest {
                adapter.submitData(it)
            }

        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest {
                binding.fragmentArticleListRefresh.isRefreshing = it.refresh is LoadState.Loading
                binding.fragmentArticleListEmptyMsg.isVisible = adapter.itemCount == 0
                binding.fragmentArticleListRecycler.isVisible = adapter.itemCount != 0
            }


        }
    }
    /*private fun makeFakeData(){
        val db = AppDatabase.getInstance(requireContext())
        if(db.articleDao().getArticleCount() >= 5){
            return
        }
        val articleUuid = Date().time.toString()
        val article = Article(articleUuid,"Test Article: $articleUuid","Will","2020年8月27日19:59:28",5,"link: $articleUuid","sefie")
        val pictures: ArrayList<Picture> = ArrayList()
        for(i in 0..5){
            val pictureUuid = Date().time.toString()
            pictures.add(Picture(pictureUuid,articleUuid,"path: $articleUuid",131,"testpic:${i}","url:$articleUuid"))
        }
        ArticleRepository.getInstance(db.articleDao()).insertArticle(article)
        PictureRepository.getInstance(db.pictureDao()).insertPictures(pictures)
        requireActivity().runOnUiThread{Toast.makeText(context,"added",Toast.LENGTH_SHORT).show()}

    }*/
    companion object{
        const val TYPE_SELFIE = "selfie"
        const val TYPE_POST = "post"
        const val TYPE_FAVORITE = "local"
        private const val SERIES = "fragment_series_list"

        fun getInstance(series: Series): Fragment {
            val instance = ArticleListFragment()
            val bundle = Bundle().apply {
                putSerializable(SERIES,series)
            }
            instance.arguments = bundle
            return instance
        }
        private fun getSeries(fragment: Fragment): Series{
             return fragment.requireArguments().getSerializable(SERIES) as Series? ?: Series.getLocalSeries()
        }
    }
}