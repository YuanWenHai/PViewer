package com.will.pviewer.extension

import androidx.fragment.app.FragmentTransaction
import com.will.pviewer.R

/**
 * created  by will on 2020/10/21 16:26
 */

fun FragmentTransaction.withAnimation(): FragmentTransaction{
    return setCustomAnimations(
        R.anim.slide_in_top,
        R.anim.no_anim,
        R.anim.no_anim,
        R.anim.nav_default_pop_exit_anim)
}