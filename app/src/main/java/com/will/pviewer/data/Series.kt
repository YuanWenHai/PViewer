package com.will.pviewer.data

import java.io.Serializable


data class Series(val name: String,val alias: String): Serializable {

    companion object{
        const val Selfie = "selfie"
        const val Post = "post"
        const val Local = "local"


        fun getFavoriteSeries(): Series {
            return Series("Favorite", Local)
        }
        fun getSelfieSeries(): Series{
            return Series("Selfie",Selfie)
        }
        fun getPostSeries(): Series{
            return Series("Post", Post)
        }
    }
}