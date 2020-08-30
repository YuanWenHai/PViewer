package com.will.pviewer.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.will.pviewer.data.Picture
import com.will.pviewer.databinding.ItemArticleBinding

class ArticlePictureAdapter(): ListAdapter<Picture, ArticlePictureAdapter.ViewHolder>(Callback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class ViewHolder(private val binding: ItemArticleBinding): RecyclerView.ViewHolder(binding.root){

    }
}
private class Callback(): DiffUtil.ItemCallback<Picture>(){
    override fun areItemsTheSame(oldItem: Picture, newItem: Picture): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Picture, newItem: Picture): Boolean {
        return oldItem.url == newItem.url
    }
}









