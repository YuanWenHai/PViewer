package com.will.pviewer.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

/**
 * created  by will on 2020/8/23 15:10
 */
@Dao
interface ArticleWithPicturesDao {

    @Transaction
    @Query("SELECT * FROM articles WHERE id = :id LIMIT 1")
    fun getArticleWithPicturesById(id: Int): LiveData<ArticleWithPictures>

    //todo
    //fun getArticlesWithPicturesByPage(pageSize: Int,pageCount: Int): LiveData<List<ArticleWithPictures>>

    @Transaction
    @Query("SELECT * FROM articles")
    fun getAllArticlesWithPictures(): LiveData<List<ArticleWithPictures>>
}