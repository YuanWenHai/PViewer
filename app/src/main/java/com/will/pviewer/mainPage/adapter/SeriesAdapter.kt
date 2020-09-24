package com.will.pviewer.mainPage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.will.pviewer.R
import com.will.pviewer.databinding.ItemFragmentSeriesBinding
import com.will.pviewer.network.SeriesResponse
import kotlinx.android.synthetic.main.item_fragment_series.view.*

/**
 * created  by will on 2020/9/24 11:49
 */
class SeriesAdapter(): ListAdapter<String,SeriesAdapter.SeriesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val binding = DataBindingUtil.inflate<ItemFragmentSeriesBinding>(LayoutInflater.from(parent.context),
            R.layout.item_fragment_series,parent,false)
        return SeriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SeriesViewHolder(private val binding: ItemFragmentSeriesBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(series: String){
            binding.itemFragmentSeries.text = series
        }
    }
}
 private class DiffCallback: DiffUtil.ItemCallback<String>() {
     override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
         return oldItem == newItem
     }

     override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
         return oldItem == newItem
     }
 }