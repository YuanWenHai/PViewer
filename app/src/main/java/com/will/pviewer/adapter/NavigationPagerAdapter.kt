package com.will.pviewer.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.will.pviewer.R
import com.will.pviewer.data.Series
import com.will.pviewer.mainPage.*

/**
 * created  by will on 2020/8/29 16:00
 */
class NavigationPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return NavigationItems.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return NavigationItems.values()[position].getFragment()
    }
}
private enum class NavigationItems(val index: Int){
    Selfie(0){
        override fun getIdRes(): Int {
            return R.id.selfie
        }
    }
    ,Post(1){
        override fun getIdRes(): Int {
            return R.id.post
        }
    }
    ,Series(2){
        override fun getIdRes(): Int {
            return R.id.series
        }
    }
    ,Personal(3){
        override fun getIdRes(): Int {
            return R.id.personal
        }
    };

    fun getFragment(): Fragment {
        return when(this){
            Selfie -> SelfieListFragment()
            Post -> PostListFragment()
            Series -> SeriesFragment()
            Personal -> PersonalFragment()
        }
    }
    fun getIndexById(idRes: Int): Int{
        values().forEach {
            if (it.getIdRes() == idRes) return it.index
        }
        return 0
    }
    fun getResIdByIndex(index: Int): Int{
        values().forEach {
            if(it.index == index) return it.getIdRes()
        }
        return 0
    }
    abstract fun getIdRes(): Int
}