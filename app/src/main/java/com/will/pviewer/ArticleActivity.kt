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
        initializeView()
    }

    private fun initializeView(){
        val binding: ActivityArticleBinding = DataBindingUtil.setContentView(this,R.layout.activity_article)
        val adapter = ArticlePictureAdapter()
        binding.articleRecycler.adapter = adapter
        val data = makeFakeData()
        with(binding){
            viewModel = ArticleViewModel(data)
            executePendingBindings()
        }
        adapter.submitList(data.pictureList)
    }

    private fun makeFakeData(): ArticleWithPictures{
        val pictures = ArrayList<Picture>()
        val urlList = arrayOf(
            "http://contentcms-bj.cdn.bcebos.com/cmspic/b4aad1ba2d29cd93e47d48330b4118cf.jpeg",
            "http://contentcms-bj.cdn.bcebos.com/cmspic/de606b7478ea0726a5d8688906b6cdfe.jpeg",
            "http://contentcms-bj.cdn.bcebos.com/cmspic/529b2b0a6ca2c05d4147ad8cd5911ba0.jpeg",
            "http://contentcms-bj.cdn.bcebos.com/cmspic/1ecc604a5922edd4726aa24a02318688.jpeg",
            "http://contentcms-bj.cdn.bcebos.com/cmspic/3947c7307cacb72a439e8dad31aa677a.jpeg"
        )
        for(i in 0 until 5){
            pictures.add(Picture(i,1,"D:/pics",213213,"TestPic$i",urlList[i]))
        }
        val article = Article(1,"game of throne","will","10点50分",5,"http://192.168.0.1")
        return ArticleWithPictures(article,pictures)
    }
}