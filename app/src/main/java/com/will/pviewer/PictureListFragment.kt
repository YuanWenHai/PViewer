package com.will.pviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.will.pviewer.adapter.ArticlePictureAdapter
import com.will.pviewer.databinding.FragmentPictureListBinding
import com.will.pviewer.viewmodels.PictureListViewModel

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
        val binding: FragmentPictureListBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_picture_list,container,false)
        val list = binding.articleRecycler
        val adapter = ArticlePictureAdapter()
        list.adapter = adapter
        return binding.root
    }
}