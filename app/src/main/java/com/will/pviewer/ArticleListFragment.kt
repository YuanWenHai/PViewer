package com.will.pviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.will.pviewer.adapter.ArticleListAdapter
import com.will.pviewer.data.*
import com.will.pviewer.databinding.FragmentArticleListBinding
import com.will.pviewer.viewmodels.ArticleListViewModel
import com.will.pviewer.viewmodels.ArticleListViewModelFactory

/**
 * created  by will on 2020/8/23 16:29
 */
class ArticleListFragment: Fragment() {

    private val viewModel: ArticleListViewModel by viewModels{
        ArticleListViewModelFactory(ArticleWithPicturesRepository.getInstance(AppDatabase.getInstance(requireContext()).articleWithPicturesDao()))
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentArticleListBinding.inflate(inflater,container,false)
        val adapter = ArticleListAdapter()
        binding.fragmentArticleListRecycler.adapter = adapter
        binding.fragmentArticleListButton.setOnClickListener{
            Toast.makeText(context,"clicked",Toast.LENGTH_SHORT).show()
        }
        subscribeUi(adapter)
        return binding.root
    }
    private fun subscribeUi(adapter: ArticleListAdapter){
        viewModel.articleWithPictures.observe(viewLifecycleOwner){result ->
            adapter.submitList(result)
        }
    }
    private fun makeFakeData(){
        val article = Article(999,"Test Article","Will","2020年8月27日19:59:28",5)
        val pictures: ArrayList<Picture> = ArrayList()
        for(i in 0..5){
            pictures.add(Picture(i,999,"fake",131,"testpic:${i}"))
        }
    }
}