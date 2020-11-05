package com.will.pviewer.mainPage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.will.pviewer.R
import com.will.pviewer.data.Series
import com.will.pviewer.databinding.ItemFragmentSeriesBinding
import com.will.pviewer.mainPage.ArticleListActivity
import com.will.pviewer.util.Util

/**
 * created  by will on 2020/9/24 11:49
 */
class SeriesAdapter(private val onItemClick: (Series) -> Unit): ListAdapter<Series,SeriesAdapter.SeriesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val binding = DataBindingUtil.inflate<ItemFragmentSeriesBinding>(LayoutInflater.from(parent.context),
            R.layout.item_fragment_series,parent,false)
        return SeriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SeriesViewHolder(private val binding: ItemFragmentSeriesBinding): RecyclerView.ViewHolder(binding.root){
        private lateinit var series: Series
        init {
            binding.root.setOnClickListener{
                Util.preventDoubleClick(it)
                onItemClick(series)
            }
        }
        fun bind(series: Series){
            binding.itemFragmentSeries.text = series.name
            this.series = series
        }
    }
}
 private class DiffCallback: DiffUtil.ItemCallback<Series>() {
     override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean {
         return oldItem.alias == newItem.alias
     }

     override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
         return oldItem.alias == newItem.alias
     }
 }