package com.will.pviewer.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * created  by will on 2020/8/23 14:47
 */
@Dao
interface PictureDao {

    @Query("SELECT * FROM pictures WHERE id = :id LIMIT 1")
    fun getPictureById(id: Int): LiveData<Picture>

    @Insert
    fun insertPicture(picture: Picture): Long

    @Insert
    fun insertPictures(pictures: List<Picture>)
}