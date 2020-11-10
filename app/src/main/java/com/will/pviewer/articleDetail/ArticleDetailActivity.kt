package com.will.pviewer.articleDetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.will.pviewer.R
import com.will.pviewer.articleDetail.viewModel.DetailViewModel
import com.will.pviewer.data.AppDatabase
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.databinding.ActivityArticleDetailBinding
import com.will.pviewer.network.ApiServiceImp
import java.lang.IllegalArgumentException

class ArticleDetailActivity: AppCompatActivity() {

    // TODO: 2020/11/5  这里将修改数据逻辑，更新为从服务器加载
    private val detailViewModel: DetailViewModel by viewModels{

            object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return DetailViewModel(getArticleId(this@ArticleDetailActivity),AppDatabase.getInstance(this@ArticleDetailActivity).articleWithPicturesDao(),ApiServiceImp.get()) as T
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        //window.decorView.systemUiVisibility =(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
      /*  window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)*/
        super.onCreate(savedInstanceState)
        detailViewModel
        val binding = DataBindingUtil.setContentView<ActivityArticleDetailBinding>(this, R.layout.activity_article_detail)
        supportFragmentManager.beginTransaction().add(R.id.detail_container,PictureListFragment(),null).commit() //initialize()
    }


    companion object{
        //private const val ARTICLE_ACTIVITY_DATA = "article_activity_data"
        private const val ARTICLE_ID = "article_id"
        fun buildIntent(activity: Activity,articleWithPictures: ArticleWithPictures): Intent{
            return Intent(activity,ArticleDetailActivity::class.java).apply {
                putExtra(ARTICLE_ID,articleWithPictures.article.id)
            }
        }
        fun getArticleId(activity: ArticleDetailActivity): Int{
            activity.intent.getSerializableExtra(ARTICLE_ID)?.let {
                return it as Int
            }
            throw IllegalArgumentException("error on start activity,caused by article data missing")
        }
    }

}