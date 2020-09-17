package com.will.pviewer

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.will.pviewer.adapter.ArticlePictureAdapter
import com.will.pviewer.data.Article
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.Picture
import com.will.pviewer.databinding.ActivityArticleBinding
import com.will.pviewer.viewmodels.ArticleViewModel

val ARTICLE_ACTIVITY_DATA = "article_activity_data"
class ArticleActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
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