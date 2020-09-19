package com.will.pviewer.articleDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.will.pviewer.R
import com.will.pviewer.data.Picture
import com.will.pviewer.databinding.ItemFragmentGalleryBinding

/**
 * created  by will on 2020/9/19 17:43
 */
class PhotoFragment private constructor(): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ItemFragmentGalleryBinding = DataBindingUtil.inflate(inflater, R.layout.item_fragment_gallery,container,false)
        val picture = getPicture(this)
        Glide.with(binding.itemFragmentGalleryPhoto).load(picture.url).into(binding.itemFragmentGalleryPhoto)
        return binding.root
    }
    companion object{
        private const val PICTURE = "data_picture"
        fun get(picture: Picture): PhotoFragment{
            return PhotoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(PICTURE,picture)
                }
            }
        }
        fun getPicture(photoFragment: PhotoFragment): Picture{
            return photoFragment.arguments?.getSerializable(PICTURE) as Picture

        }
    }
}