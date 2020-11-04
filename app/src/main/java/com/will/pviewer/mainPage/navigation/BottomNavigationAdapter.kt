package com.will.pviewer.mainPage.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * created  by will on 2020/11/4 17:30
 */
class BottomNavigationAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return NavigationItems.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return NavigationItems.values()[position].fragment()
    }
}