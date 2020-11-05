package com.will.pviewer.mainPage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.will.pviewer.R
import com.will.pviewer.data.Series
import com.will.pviewer.databinding.FragmentPersonalBinding
import com.will.pviewer.extension.withAnimation
import com.will.pviewer.util.Util

/**
 * created  by will on 2020/9/24 11:22
 */
class PersonalFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPersonalBinding>(inflater,R.layout.fragment_personal,container,false)
        initialize(binding)
        return binding.root
    }

    private fun initialize(binding: FragmentPersonalBinding){
        binding.fragmentPersonalFavorite.setOnClickListener {
            Util.preventDoubleClick(it)
            parentFragmentManager.beginTransaction().withAnimation().replace(R.id.main_container,FavoriteListFragment()).addToBackStack(null).setReorderingAllowed(true).commit()
        }
    }
}