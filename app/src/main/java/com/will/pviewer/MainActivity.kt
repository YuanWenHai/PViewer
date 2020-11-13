package com.will.pviewer

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.will.pviewer.articleDetail.service.DownloadService
import com.will.pviewer.data.AppDatabase
import com.will.pviewer.data.ArticleWithPicturesRepository
import com.will.pviewer.databinding.ActivityMainBinding
import com.will.pviewer.mainPage.navigation.NavigationFragment
import com.will.pviewer.mainPage.viewModel.MainViewModel
import com.will.pviewer.network.ApiServiceImp

//todo
// 3.launching frame skipping
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
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.mainToolbar)
        binding.mainToolbar.setNavigationOnClickListener{
            onBackPressed()
        }
        mainViewModel.toolbarTitle.observe(this){
            binding.mainToolbar.title = it
        }


        lateInit(binding)
        binding.root.post{
            if(savedInstanceState == null){
                initDownloadService()
            }
        }
    }

    private fun lateInit(binding: ActivityMainBinding){
        binding.root.post{
            supportFragmentManager.beginTransaction().add(R.id.main_container,NavigationFragment()).commit()
            supportFragmentManager.addOnBackStackChangedListener{
                supportActionBar?.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 0)
            }
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
}