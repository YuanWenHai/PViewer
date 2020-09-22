package com.will.pviewer.articleList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.will.pviewer.articleList.adapter.ArticleListAdapter
import com.will.pviewer.articleList.adapter.ArticleListLoadStateAdapter
import com.will.pviewer.articleList.adapter.LocalArticleListAdapter
import com.will.pviewer.data.*
import com.will.pviewer.databinding.FragmentArticleListBinding
import com.will.pviewer.setting.LOG_TAG
import com.will.pviewer.articleList.viewModel.ArticleListViewModel
import com.will.pviewer.articleList.viewModel.ArticleListViewModelFactory
import kotlinx.coroutines.flow.collectLatest

/**
 * created  by will on 2020/8/23 16:29
 */
class ArticleListFragment private  constructor(): Fragment() {


    private val viewModel: ArticleListViewModel by viewModels{
        ArticleListViewModelFactory(ArticleWithPicturesRepository.getInstance(AppDatabase.getInstance(requireContext()).articleWithPicturesDao()))
    }

    private fun getAdapter(): PagingDataAdapter<ArticleWithPictures,out RecyclerView.ViewHolder>{
        return when(getType(this)){
            TYPE_SELFIE -> ArticleListAdapter()
            TYPE_POST -> ArticleListAdapter()
            TYPE_FAVORITE -> LocalArticleListAdapter()
            else -> ArticleListAdapter()
        }
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

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            if(getType(this@ArticleListFragment) == TYPE_FAVORITE){
                viewModel.localArticles.collectLatest {
                    Log.d(LOG_TAG,"start collect articles from DB ")
                    adapter.submitData(it)
                }
            }else{
                viewModel.articles.collectLatest {
                    Log.d(LOG_TAG,"start collect articles from server")
                    adapter.submitData(it)
                }
                viewModel.articles
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest {
                binding.fragmentArticleListRefresh.isRefreshing = it.refresh is LoadState.Loading
                //binding.fragmentArticleListEmptyMsg.isVisible = adapter.itemCount == 0
                //binding.fragmentArticleListRecycler.isVisible = adapter.itemCount != 0
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
        const val TYPE_SELFIE = 0
        const val TYPE_POST = 1
        const val TYPE_FAVORITE = 2
        private const val TYPE = "fragment_type"

        fun getInstance(type: Int): Fragment {
            val instance = ArticleListFragment()
            val bundle = Bundle().apply {
                putInt(TYPE,type)
            }
            instance.arguments = bundle
            return instance
        }
        private fun getType(fragment: Fragment): Int{
             return fragment.requireArguments().getInt(TYPE,0)
        }
    }
}