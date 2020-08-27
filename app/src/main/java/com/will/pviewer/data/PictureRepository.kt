package com.will.pviewer.data

class PictureRepository private constructor(private val dao: PictureDao){


    fun insertPicture(picture: Picture) = dao.insertPicture(picture)
    fun insetPictures(pictures: List<Picture>) = dao.insertPictures(pictures)


    companion object{
        @Volatile private var instance: PictureRepository? = null
        fun getInstance(dao: PictureDao): PictureRepository{
            return instance ?: synchronized(this){
                instance ?: PictureRepository(dao).also { instance = it }
            }
        }

    }
}