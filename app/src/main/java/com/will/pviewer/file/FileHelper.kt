package com.will.pviewer.file

import android.util.Log
import com.will.pviewer.setting.LOG_TAG
import java.io.*

/**
 * created  by will on 2020/9/20 18:08
 */
class FileHelper {

    companion object {
        fun writeStreamToFile(stream: InputStream, destFile: File){
            val writer = FileOutputStream(destFile)
            writer.write(stream.readBytes())
        }

        fun deleteFile(file: File){
            if(!file.exists() || !file.isFile){
                Log.e(LOG_TAG,"try to delete a file that does not exits or not file")
                return
            }
            file.delete()
        }
        fun deleteDirectoryRec(dir: File){
            if(!dir.exists() || !dir.isDirectory){
                Log.e(LOG_TAG,"try to delete dir that does not exist of not directory")
                return
            }
            dir.deleteRecursively()
        }
    }

}