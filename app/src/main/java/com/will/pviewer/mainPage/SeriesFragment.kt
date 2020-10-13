package com.will.pviewer.mainPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import com.will.pviewer.R
import com.will.pviewer.databinding.FragmentSeriesListBinding
import com.will.pviewer.mainPage.adapter.SeriesAdapter
import com.will.pviewer.mainPage.viewModel.SeriesViewModel
import com.will.pviewer.network.ApiServiceImp

/**
 * created  by will on 2020/9/24 11:22
 */
class SeriesFragment: Fragment() {
    val viewModel: SeriesViewModel by viewModels {
        object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SeriesViewModel(ApiServiceImp.get()) as T
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSeriesListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_series_list,container,false)
        val adapter = SeriesAdapter()
        binding.fragmentSeriesListRecycler.addItemDecoration(DividerItemDecoration(requireContext(),LinearLayout.VERTICAL))
        binding.fragmentSeriesListRefresh.setOnRefreshListener {viewModel.getSeries()}
        binding.fragmentSeriesListRecycler.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.getSeries().observe(viewLifecycleOwner){
                adapter.submitList(it)
                binding.fragmentSeriesListEmptyMsg.isVisible = adapter.itemCount == 0
                binding.fragmentSeriesListRecycler.isVisible = adapter.itemCount != 0
            }

        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.isLoading().observe(viewLifecycleOwner){
                binding.fragmentSeriesListRefresh.isRefreshing = it
            }

        }


        return binding.root
    }

    private fun initialize(){

    }
}