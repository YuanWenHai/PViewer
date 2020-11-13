package com.will.pviewer.setting

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File

/**
 * created  by will on 2020/8/23 15:04
 */
const val DATABASE_NAME = "pviewer-db"

const val LOG_TAG = "!~"

const val LOCAL_PICTURE_RELATIVE_PATH = "/local/"

fun getLocalDirectory(context: Context): File?{
    val base = context.externalCacheDir
    return if(base == null){
        base
    }else{
        val dir = File(base.path + LOCAL_PICTURE_RELATIVE_PATH)
        dir.mkdirs()
        dir
    }
}

fun getDownloadRootDir(context: Context): File?{
    val externalAvailable = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    if(externalAvailable){
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    }
    Log.e(LOG_TAG, "externalAvailable is false,cannot get app external storage directory")
    return null
}