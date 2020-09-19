package com.will.pviewer.articleDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.will.pviewer.R
import com.will.pviewer.articleDetail.adapter.PictureAdapter
import com.will.pviewer.databinding.FragmentPictureListBinding
import com.will.pviewer.viewmodels.ArticleViewModel
import com.will.pviewer.articleDetail.viewModel.PictureListViewModel
import com.will.pviewer.setting.LOG_TAG

/**
 * created  by will on 2020/9/18 17:57
 */
class PictureListFragment(): Fragment() {
    val viewModel: PictureListViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPictureListBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_picture_list,container,false)
        val list = binding.articleRecycler
        val adapter = PictureAdapter{
            viewModel.currentIndex.value = it
            val gallery = GalleryFragment()
            parentFragmentManager.beginTransaction().add(R.id.activity_article_detail_container,gallery,null).addToBackStack(null).commit()
        }
        list.adapter = adapter
        viewModel.getArticle().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.pictureList)
            binding.viewModel = ArticleViewModel(it)
            Log.e("!~","picture list size is ${it.pictureList.size}")

        })

        return binding.root
    }
}