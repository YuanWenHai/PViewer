package com.will.pviewer.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

/**
 * created  by will on 2020/8/23 14:33
 */
@Dao
interface ArticleDao {

    fun getArticlesByPage(pageSize: Int,pageCount: Int ): LiveData<List<Article>>
    fun getArticlesByAuthor(author: String): LiveData<List<Article>>


    @Query("SELECT * FROM articles WHERE id = :id LIMIT 1")
    fun getArticleById(id: Int): LiveData<Article>
}