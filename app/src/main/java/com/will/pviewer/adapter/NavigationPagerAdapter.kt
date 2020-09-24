package com.will.pviewer.adapter

import androidx.core.app.Person
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.will.pviewer.mainPage.ArticleListFragment
import com.will.pviewer.mainPage.PersonalFragment
import com.will.pviewer.mainPage.SeriesFragment

/**
 * created  by will on 2020/8/29 16:00
 */
class NavigationPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return ArticleListFragment.getInstance(position)
    }
}
enum class NavigationItems(val index: Int){
    Selfie(0)
    ,Post(1)
    ,Series(2)
    ,Personal(3);

    fun get(item: NavigationItems): Fragment {
        return when(item){
            Selfie -> ArticleListFragment.getInstance(ArticleListFragment.TYPE_SELFIE)
            Post -> ArticleListFragment.getInstance(ArticleListFragment.TYPE_POST)
            Series -> SeriesFragment()
            Personal -> PersonalFragment()
        }
    }
}