package com.will.pviewer.mainPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import com.will.pviewer.R
import com.will.pviewer.databinding.FragmentSeriesListBinding
import com.will.pviewer.extension.withAnimation
import com.will.pviewer.mainPage.adapter.SeriesAdapter
import com.will.pviewer.mainPage.viewModel.MainViewModel

/**
 * created  by will on 2020/9/24 11:22
 */
class SeriesFragment: Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSeriesListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_series_list,container,false)
        val adapter = SeriesAdapter{
            parentFragmentManager.beginTransaction().withAnimation().replace(R.id.main_container,ArticleListFragmentImp.get(it)).addToBackStack("will").commit()
        }
        binding.fragmentSeriesListRefresh.setColorSchemeResources(R.color.colorPrimary)
        binding.fragmentSeriesListRecycler.addItemDecoration(DividerItemDecoration(requireContext(),LinearLayout.VERTICAL))
        binding.fragmentSeriesListRefresh.setOnRefreshListener {mainViewModel.getSeries()}
        binding.fragmentSeriesListRecycler.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            mainViewModel.getSeries()
            mainViewModel.seriesList.observe(viewLifecycleOwner){
                adapter.submitList(it)
                binding.fragmentSeriesListEmptyMsg.isVisible = adapter.itemCount == 0
                binding.fragmentSeriesListRecycler.isVisible = adapter.itemCount != 0
            }

        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            mainViewModel.isSeriesListLoading.observe(viewLifecycleOwner){
                binding.fragmentSeriesListRefresh.isRefreshing = it
            }

        }
        return binding.root
    }

}