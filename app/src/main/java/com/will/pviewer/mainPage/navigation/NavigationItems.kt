package com.will.pviewer.mainPage.navigation

import androidx.fragment.app.Fragment
import com.will.pviewer.R
import com.will.pviewer.mainPage.PersonalFragment
import com.will.pviewer.mainPage.PostListFragment
import com.will.pviewer.mainPage.SelfieListFragment
import com.will.pviewer.mainPage.SeriesFragment
import java.lang.IllegalArgumentException

/**
 * created  by will on 2020/11/4 17:32
 */
enum class NavigationItems {

    Selfie{
        override fun fragment(): Fragment {
            return SelfieListFragment()
        }

        override fun label(): String {
            return "Selfie"
        }

        override fun menuItemId(): Int {
            return R.id.selfie
        }

        override fun index(): Int {
            return 0
        }
    },
    Post{
        override fun fragment(): Fragment {
            return PostListFragment()
        }

        override fun label(): String {
            return "Post"
        }

        override fun menuItemId(): Int {
            return R.id.post
        }
        override fun index(): Int {
            return 1
        }
    },
    Series{
        override fun fragment(): Fragment {
            return SeriesFragment()
        }

        override fun label(): String {
            return "Series"
        }

        override fun menuItemId(): Int {
            return R.id.series
        }
        override fun index(): Int {
            return 2
        }
    },
    Personal{
        override fun fragment(): Fragment {
            return PersonalFragment()
        }

        override fun label(): String {
            return "Personal"
        }

        override fun menuItemId(): Int {
            return R.id.personal
        }
        override fun index(): Int {
            return 3
        }
    };



    abstract fun fragment(): Fragment
    abstract fun label(): String
    abstract fun menuItemId(): Int
    abstract fun index(): Int
    companion object{
        fun get(menuItemId: Int): NavigationItems{
            values().forEach {
                if (it.menuItemId() == menuItemId){
                    return it
                }
            }
            throw IllegalArgumentException("no menuItemId matched")
        }

    }
}