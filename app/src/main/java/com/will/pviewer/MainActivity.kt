package com.will.pviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.will.pviewer.adapter.NavigationItems
import com.will.pviewer.adapter.NavigationPagerAdapter
import com.will.pviewer.databinding.ActivityMainBinding

//todo 1.foreground download service 2.androidx-navigation 3.socket timeout exception catching 4.launching frame skipping

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeView()
    }

    private fun initializeView(){
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val navigation = binding.mainNavigationView
        val pager = binding.mainViewPager

        val pagerAdapter = NavigationPagerAdapter(this)
        pager.offscreenPageLimit = 4
        pager.adapter = pagerAdapter

        pager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                navigation.selectedItemId = NavigationItems.Personal.getResIdByIndex(position)
            }
        })
        navigation.setOnNavigationItemSelectedListener {
            pager.setCurrentItem(NavigationItems.Personal.getIndexById(it.itemId),true
            )
            true
        }


    }
}