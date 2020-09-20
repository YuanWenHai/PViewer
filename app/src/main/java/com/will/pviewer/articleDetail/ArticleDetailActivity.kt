package com.will.pviewer.articleDetail

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.will.pviewer.R
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.databinding.ActivityArticleDetailBinding
import com.will.pviewer.articleDetail.viewModel.PictureListViewModel
import com.will.pviewer.articleDetail.viewModel.PictureListViewModelFactory

const val ARTICLE_ACTIVITY_DATA = "article_activity_data"
class ArticleActivity: AppCompatActivity() {
    private val viewModel: PictureListViewModel by viewModels{
        val article = intent.getSerializableExtra(ARTICLE_ACTIVITY_DATA) as ArticleWithPictures
        PictureListViewModelFactory(article)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        //window.decorView.systemUiVisibility =(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
      /*  window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)*/

        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityArticleDetailBinding>(this,
            R.layout.activity_article_detail
        )
        viewModel
        val list = PictureListFragment()
        supportFragmentManager.beginTransaction().add(R.id.activity_article_detail_container,list,null).commit() //initialize()
    }

   /* private fun initialize(){
        val binding: ActivityArticleBinding = DataBindingUtil.setContentView(this,R.layout.activity_article_detail)
        val adapter = PictureAdapter()
        binding.articleRecycler.adapter = adapter
        val article = intent.getSerializableExtra(ARTICLE_ACTIVITY_DATA)
        if(article is ArticleWithPictures){
            with(binding){
                viewModel = ArticleViewModel(article)
                executePendingBindings()
            }
            adapter.submitList(article.pictureList)
        }
    }*/


}