package com.will.pviewer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.will.pviewer.setting.DATABASE_NAME

/**
 * created  by will on 2020/8/23 14:52
 */
@Database(entities = [Article::class, Picture::class],version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun pictureDao(): PictureDao
    abstract fun articleWithPicturesDao(): ArticleWithPicturesDao

    companion object{
        @Volatile private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this){
                instance ?: buildDatabase(context).also { instance = it }
            }
        }


        private fun buildDatabase(context: Context): AppDatabase{
            return Room.databaseBuilder(context,AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object: Callback(){
                    // TODO: 2020/8/23 pre-initialize
                }).build()
        }

    }
}