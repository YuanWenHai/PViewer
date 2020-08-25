package com.will.pviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.will.pviewer.adapter.ArticleListAdapter
import com.will.pviewer.data.AppDatabase
import com.will.pviewer.data.ArticleWithPicturesRepository
import com.will.pviewer.databinding.FragmentArticleListBinding
import com.will.pviewer.viewmodels.ArticleListViewModel
import com.will.pviewer.viewmodels.ArticleListViewModelFactory
import com.will.pviewer.viewmodels.ArticleViewModel

/**
 * created  by will on 2020/8/23 16:29
 */
class ArticleListFragment: Fragment() {

    private val viewModel: ArticleListViewModel by viewModels{
        ArticleListViewModelFactory(ArticleWithPicturesRepository.getInstance(AppDatabase.getInstance(requireContext()).articleWithPicturesDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentArticleListBinding.inflate(inflater,container,false)
        val adapter = ArticleListAdapter()

        return binding.root
    }
    private fun subscribeUi(adapter: ArticleListAdapter){

    }
}