package com.will.pviewer

import android.content.Intent
import android.os.Bundle
import android.os.HandlerThread
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavController
import com.will.pviewer.articleDetail.service.DownloadService
import com.will.pviewer.data.AppDatabase
import com.will.pviewer.data.ArticleWithPicturesRepository
import com.will.pviewer.databinding.ActivityMainBinding
import com.will.pviewer.mainPage.navigation.NavigationFragment
import com.will.pviewer.mainPage.navigation.NavigationItems
import com.will.pviewer.mainPage.viewModel.AppViewModel
import com.will.pviewer.mainPage.viewModel.MainViewModel
import com.will.pviewer.network.ApiServiceImp
import com.will.pviewer.setting.LOG_TAG

//todo
// 1.foreground download service and picturelist fragment's status change,service binding .etc
// 2.androidx-navigation
// 3.launching frame skipping
// 4.favorite delete
// 5.storage statistic and management
class MainActivity : AppCompatActivity() {


    private val mainViewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(
                    ApiServiceImp.get(),
                    ArticleWithPicturesRepository.getInstance(
                        AppDatabase.getInstance(this@MainActivity).articleWithPicturesDao()
                    )
                ) as T
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.mainToolbar)
        binding.mainToolbar.setNavigationOnClickListener{
            onBackPressed()
        }
        mainViewModel.toolbarTitle.observe(this){
            binding.mainToolbar.title = it
        }
        supportFragmentManager.beginTransaction().add(R.id.main_container,NavigationFragment()).commit()
        supportFragmentManager.addOnBackStackChangedListener{
            supportActionBar?.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 0)
        }
        if(savedInstanceState == null){
            initDownloadService()
        }
    }


    private fun initDownloadService(){
        val intent = Intent(this,DownloadService::class.java)
        startService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent(this,DownloadService::class.java)
        stopService(intent)
    }


   /* private fun init() {
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
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentController?.value?.navigateUp() ?: false

    }*/

}