package com.will.pviewer.util

import android.content.Context
import android.view.View
import android.widget.Toast

/**
 * created  by will on 2020/9/29 15:20
 */
class Util {

    companion object{
        fun preventDoubleClick(view: View){
            view.isEnabled = false
            view.postDelayed({
                view.isEnabled = true
            },1000)
        }

        fun makeToast(context: Context,content: String){
            Toast.makeText(context,content,Toast.LENGTH_SHORT)
        }
    }
}