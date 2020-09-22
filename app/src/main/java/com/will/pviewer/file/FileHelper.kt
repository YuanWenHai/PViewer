package com.will.pviewer.file

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
    }


}