package com.will.pviewer.setting

import android.content.Context
import android.os.Environment
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