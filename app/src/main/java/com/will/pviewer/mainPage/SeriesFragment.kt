package com.will.pviewer.mainPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import com.will.pviewer.R
import com.will.pviewer.databinding.FragmentSeriesBinding
import com.will.pviewer.mainPage.adapter.SeriesAdapter
import com.will.pviewer.mainPage.viewModel.SeriesViewModel
import com.will.pviewer.network.ApiService

/**
 * created  by will on 2020/9/24 11:22
 */
class SeriesFragment: Fragment() {
    val viewModel: SeriesViewModel by viewModels {
        object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SeriesViewModel(ApiService.getApiService()) as T
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSeriesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_series,container,false)
        val adapter = SeriesAdapter()
        binding.fragmentSeriesRecycler.addItemDecoration(DividerItemDecoration(requireContext(),LinearLayout.VERTICAL))
        binding.fragmentSeriesRefresh.setOnRefreshListener {viewModel.getSeries()}
        binding.fragmentSeriesRecycler.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getSeries().observe(viewLifecycleOwner){
                adapter.submitList(it)
                binding.fragmentSeriesRefresh.isRefreshing = false
            }
        }


        return binding.root
    }

    private fun initialize(){

    }
}