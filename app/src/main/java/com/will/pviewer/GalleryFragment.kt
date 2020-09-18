package com.will.pviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.will.pviewer.databinding.FragmentGalleryBinding
import java.util.zip.Inflater

/**
 * created  by will on 2020/9/18 17:21
 */
class GalleryFragment(): Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentGalleryBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_gallery,container,false)

        return binding.root
    }
}