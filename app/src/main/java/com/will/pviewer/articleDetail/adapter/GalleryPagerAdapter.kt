package com.will.pviewer.articleDetail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.will.pviewer.articleDetail.PhotoFragment
import com.will.pviewer.data.Picture

/**
 * created  by will on 2020/9/18 17:43
 */
class GalleryPagerAdapter(activity: FragmentActivity,private val picList: List<Picture>): FragmentStateAdapter(activity){

    override fun getItemCount(): Int {
        return picList.size
    }

    override fun createFragment(position: Int): Fragment {
        return PhotoFragment.get(picList[position])
    }
}