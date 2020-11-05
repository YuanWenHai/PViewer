package com.will.pviewer.mainPage.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import com.will.pviewer.R
import com.will.pviewer.databinding.ActivityMainBinding
import com.will.pviewer.databinding.FragmentNavigationBinding
import com.will.pviewer.mainPage.viewModel.MainViewModel

/**
 * created  by will on 2020/11/4 17:49
 */
class NavigationFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNavigationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_navigation,container,false)
        initNavigation(binding)
        return binding.root
    }

    private fun initNavigation(binding: FragmentNavigationBinding){

        val navigationAdapter = BottomNavigationAdapter(requireActivity())
        binding.navigationViewpager.adapter = navigationAdapter
        binding.navigationViewpager.offscreenPageLimit = 3
        binding.navigationViewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.navigationView.selectedItemId = NavigationItems.values()[position].menuItemId()
            }
        })

        binding.navigationView.setOnNavigationItemSelectedListener {
            binding.navigationViewpager.currentItem = NavigationItems.get(it.itemId).index()
            true
        }
    }

}