package com.will.pviewer.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

/**
 * created  by will on 2020/8/23 15:10
 */
@Dao
interface ArticleWithPicturesDao {


    @Transaction
    @Query("SELECT * FROM articles WHERE id = :id")
    fun getArticleWIthPicturesById(id: Int): ArticleWithPictures?

    @Query("DELETE FROM articles WHERE id = :id ")
    fun deleteArticleById(id: Int)

    @Query("DELETE FROM pictures WHERE articleId = :articleId")
    fun deletePictureByArticleId(articleId: Int)
    //todo
    //fun getArticlesWithPicturesByPage(pageSize: Int,pageCount: Int): LiveData<List<ArticleWithPictures>>

    @Transaction
    @Query("SELECT * FROM articles")
    fun getAllArticleWithPictures(): LiveData<List<ArticleWithPictures>>

    @Transaction
    @Query("SELECT * FROM articles")
    fun getAllArticleWithPicturesInPaging(): PagingSource<Int,ArticleWithPictures>


}