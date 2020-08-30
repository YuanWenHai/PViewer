package com.will.pviewer

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.will.pviewer.databinding.ActivityArticleBinding

class ArticleActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    private fun initializeView(){
        val binding: ActivityArticleBinding = DataBindingUtil.setContentView(this,R.layout.activity_article)

    }
}