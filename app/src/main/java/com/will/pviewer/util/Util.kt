package com.will.pviewer.util

import android.view.View

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
    }
}