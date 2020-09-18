package com.will.pviewer

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.will.pviewer.adapter.ArticlePictureAdapter
import com.will.pviewer.data.Article
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.Picture
import com.will.pviewer.databinding.ActivityArticleBinding
import com.will.pviewer.viewmodels.ArticleViewModel
import com.will.pviewer.viewmodels.PictureListViewModel

val ARTICLE_ACTIVITY_DATA = "article_activity_data"
class ArticleActivity: AppCompatActivity() {
    private val viewModel: PictureListViewModel by viewModels{
        object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val article = intent.getSerializableExtra(ARTICLE_ACTIVITY_DATA) as ArticleWithPictures
                return PictureListViewModel(article) as T
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val list = PictureListFragment()
        supportFragmentManager.beginTransaction().add(list,null).commit()
        //initialize()
    }

    private fun initialize(){
        val binding: ActivityArticleBinding = DataBindingUtil.setContentView(this,R.layout.activity_article)
        val adapter = ArticlePictureAdapter()
        binding.articleRecycler.adapter = adapter
        val article = intent.getSerializableExtra(ARTICLE_ACTIVITY_DATA)
        if(article is ArticleWithPictures){
            with(binding){
                viewModel = ArticleViewModel(article)
                executePendingBindings()
            }
            adapter.submitList(article.pictureList)
        }
    }


}