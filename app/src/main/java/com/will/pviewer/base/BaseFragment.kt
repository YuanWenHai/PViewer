package com.will.pviewer.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * created  by will on 2020/11/3 18:16
 */
open class BaseFragment: Fragment(){

    private var rootView: View? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView?.let {
            return it
        }
        return null
    }

    override fun onDestroyView() {
        rootView = view
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        rootView = null
    }

}