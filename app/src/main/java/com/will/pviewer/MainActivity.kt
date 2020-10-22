package com.will.pviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.will.pviewer.adapter.NavigationItems
import com.will.pviewer.adapter.NavigationPagerAdapter
import com.will.pviewer.data.AppDatabase
import com.will.pviewer.data.ArticleWithPicturesRepository
import com.will.pviewer.databinding.ActivityMainBinding
import com.will.pviewer.extension.setupWithNavController
import com.will.pviewer.mainPage.viewModel.ArticleListViewModel
import com.will.pviewer.mainPage.viewModel.ArticleListViewModelFactory

//todo
// 1.foreground download service and picturelist fragment's status change,service binding .etc
// 2.androidx-navigation
// 3.launching frame skipping
// 4.favorite delete
class MainActivity : AppCompatActivity() {

    private val viewModel: ArticleListViewModel by viewModels{
        ArticleListViewModelFactory(
            ArticleWithPicturesRepository.getInstance(AppDatabase.getInstance(this).articleWithPicturesDao()),
            getSeries().alias)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //initializeView()
        init()
    }


    private fun init(){
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val navGraphIds = listOf(R.navigation.selfie,R.navigation.post,R.navigation.series,R.navigation.personal)
        binding.mainNavigationView.setupWithNavController(navGraphIds,supportFragmentManager,R.id.main_container)
    }

    /*private fun initializeView(){
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


    }*/
}