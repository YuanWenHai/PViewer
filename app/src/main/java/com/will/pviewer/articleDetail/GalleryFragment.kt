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
import com.will.pviewer.articleDetail.adapter.GalleryPagerAdapter
import com.will.pviewer.articleDetail.viewModel.DetailViewModel
import com.will.pviewer.databinding.FragmentGalleryBinding
import com.will.pviewer.setting.LOG_TAG


/**
 * created  by will on 2020/9/18 17:21
 */
class GalleryFragment(): Fragment(){
    private val detailViewModel: DetailViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentGalleryBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_gallery, container, false
        )

        binding.fragmentGalleryPager.offscreenPageLimit = 3
        detailViewModel.article.observe(viewLifecycleOwner, Observer {
            binding.fragmentGalleryPager.adapter = GalleryPagerAdapter(requireActivity(), it.pictureList)
        })
        detailViewModel.currentIndex.observe(viewLifecycleOwner, Observer {
            Log.d(LOG_TAG, "clicked picture item index is: $it")
            binding.fragmentGalleryPager.setCurrentItem(it, false)
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.statusBarColor = resources.getColor(R.color.galleryBackground)
    }

    override fun onPause() {
        super.onPause()
        requireActivity().window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
    }

}