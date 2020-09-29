package com.will.pviewer.data


data class Series(val name: String,val alias: String) {

    companion object{
        const val Selfie = "selfie"
        const val Post = "post"
    }
}