package com.will.pviewer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.will.pviewer.R
import com.will.pviewer.data.Picture
import com.will.pviewer.databinding.ItemArticleBinding
import com.will.pviewer.databinding.ItemArticlePictureBinding

class ArticlePictureAdapter(): ListAdapter<Picture, ArticlePictureAdapter.ViewHolder>(Callback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_article_picture,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(val binding: ItemArticlePictureBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(pic: Picture){
            Glide.with(binding.pictureImage.context).load(pic.url).into(binding.pictureImage)
        }
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









