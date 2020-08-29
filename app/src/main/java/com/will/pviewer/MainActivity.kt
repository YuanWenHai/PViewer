package com.will.pviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.will.pviewer.adapter.NavigationPagerAdapter
import com.will.pviewer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeView()
    }

    private fun initializeView(){
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val navigation = binding.mainNavigationView
        val pager = binding.mainViewPager

        pager.adapter = NavigationPagerAdapter(this)
        pager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                navigation.selectedItemId = when(position){
                    0 -> R.id.bottom_navigation_selfie
                    1 -> R.id.bottom_navigation_post
                    else -> R.id.bottom_navigation_favorite
                }
            }
        })
        navigation.setOnNavigationItemSelectedListener {
            pager.setCurrentItem(
                when(it.itemId){
                    R.id.bottom_navigation_selfie -> 0
                    R.id.bottom_navigation_post -> 1
                    else -> R.id.bottom_navigation_favorite
                },true
            )
            true
        }


    }
}