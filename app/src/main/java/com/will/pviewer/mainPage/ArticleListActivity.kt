package com.will.pviewer.mainPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.will.pviewer.R
import com.will.pviewer.data.Series
import com.will.pviewer.databinding.ActivitySeriesBinding

/**
 * created  by will on 2020/9/29 11:16
 */
class ArticleListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySeriesBinding>(this,R.layout.activity_series)
        val series = getSeries()
        val fragment = PostListFragment()
        binding.activityArticleListToolbar.title = series.name
        supportFragmentManager.beginTransaction().add(binding.activityArticleListContainer.id,fragment).commit()
        setSupportActionBar(binding.activityArticleListToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.activityArticleListToolbar.setNavigationOnClickListener{
            onBackPressed()
        }
        //supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun getSeries(): Series{
         return intent.getSerializableExtra(SERIES) as Series? ?: Series.getFavoriteSeries()
    }
    companion object{
        const val SERIES = "activity_series"
    }
}