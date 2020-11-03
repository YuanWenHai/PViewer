package com.will.pviewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.will.pviewer.adapter.NavigationItems
import com.will.pviewer.adapter.NavigationPagerAdapter
import com.will.pviewer.data.AppDatabase
import com.will.pviewer.data.ArticleWithPicturesRepository
import com.will.pviewer.databinding.ActivityMainBinding
import com.will.pviewer.extension.setupWithNavController
import com.will.pviewer.mainPage.viewModel.AppViewModel
import com.will.pviewer.mainPage.viewModel.ArticleListViewModel
import com.will.pviewer.mainPage.viewModel.ArticleListViewModelFactory
import com.will.pviewer.network.ApiService
import com.will.pviewer.network.ApiServiceImp

//todo
// 1.foreground download service and picturelist fragment's status change,service binding .etc
// 2.androidx-navigation
// 3.launching frame skipping
// 4.favorite delete
class MainActivity : AppCompatActivity() {


    private val appViewModel: AppViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return AppViewModel(
                    ApiServiceImp.get(),
                    ArticleWithPicturesRepository.getInstance(
                        AppDatabase.getInstance(this@MainActivity).articleWithPicturesDao()
                    )
                ) as T
            }
        }
    }
    private var currentController: LiveData<NavController>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appViewModel
        //initializeView()
        init()
    }


    private fun init() {
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navGraphIds = listOf(
            R.navigation.selfie,
            R.navigation.post,
            R.navigation.series,
            R.navigation.personal
        )
        currentController = binding.mainNavigationView.setupWithNavController(
            navGraphIds,
            supportFragmentManager,
            R.id.main_container
        )
        currentController?.observe(this, Observer {
            setupActionBarWithNavController(it)
        })
    }
    private var backPressedOnce = false
    override fun onBackPressed() {
        currentController?.value?.let {
            if(!it.popBackStack()){
                if(backPressedOnce){
                    finish()
                }
                val toast = Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT)
                toast.show()
                backPressedOnce = true
                toast.view.postDelayed({
                    backPressedOnce = false
                },1000)
            }
        }
        currentController?.value?.popBackStack()
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentController?.value?.navigateUp() ?: false

    }

}