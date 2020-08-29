package com.will.pviewer.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.will.pviewer.ArticleListFragment

/**
 * created  by will on 2020/8/29 16:00
 */
class NavigationPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return ArticleListFragment(position)
    }
}