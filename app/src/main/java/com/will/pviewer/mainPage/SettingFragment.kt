package com.will.pviewer.mainPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.will.pviewer.R
import com.will.pviewer.databinding.FragmentSettingBinding
import com.will.pviewer.mainPage.viewModel.SettingViewModel

/**
 * created  by will on 2020/11/17 10:59
 */
class SettingFragment: Fragment() {
    private val viewModel: SettingViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingBinding.inflate(inflater,container,false)
        init(binding)
        return binding.root
    }

    private fun init(binding: FragmentSettingBinding){
        viewModel.getCurrentServer().observe(viewLifecycleOwner){
            binding.fragmentSettingServerText.text = "Server:  $it"
        }
        viewModel.refreshServer(requireContext())
        val parent = (requireActivity() as AppCompatActivity)
        parent.setSupportActionBar(binding.fragmentSettingToolbar)
        parent.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.fragmentSettingToolbar.setNavigationOnClickListener{
            parent.onBackPressed()
        }


        binding.fragmentSettingServerButton.setOnClickListener{
            viewModel.getOtherServer().value?.let {
                val items = arrayOf(it)
                AlertDialog.Builder(requireContext())
                    .setTitle("Change Server To")
                    .setCancelable(true)
                    .setItems(items) { _, _ ->
                        viewModel.switchServer(requireContext())
                    }.show()
            }

        }
    }

}