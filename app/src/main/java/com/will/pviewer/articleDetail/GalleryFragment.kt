package com.will.pviewer.articleDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.will.pviewer.R
import com.will.pviewer.articleDetail.adapter.GalleryPagerAdapter
import com.will.pviewer.databinding.FragmentGalleryBinding
import com.will.pviewer.articleDetail.viewModel.PictureListViewModel

/**
 * created  by will on 2020/9/18 17:21
 */
class GalleryFragment(): Fragment(){
    val viewModel: PictureListViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentGalleryBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_gallery,container,false)
        viewModel.getArticle().observe(viewLifecycleOwner, Observer {
            val adapter = GalleryPagerAdapter(requireActivity(),it.pictureList)
            binding.fragmentGalleryPager.adapter = adapter
        })
        viewModel.currentIndex.observe(viewLifecycleOwner, Observer {
            binding.fragmentGalleryPager.currentItem = it
        })
        return binding.root
    }
}