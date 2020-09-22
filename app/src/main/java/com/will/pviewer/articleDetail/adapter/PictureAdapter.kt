package com.will.pviewer.articleDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.will.pviewer.R
import com.will.pviewer.data.Picture
import com.will.pviewer.databinding.ItemArticlePictureBinding

class PictureAdapter(private val onClick:(Int) -> Unit): ListAdapter<Picture, PictureAdapter.ViewHolder>(Callback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(onClick,DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_article_picture,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val onClick:(Int) -> Unit,val binding: ItemArticlePictureBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.pictureImage.setOnClickListener(){
                onClick(absoluteAdapterPosition)
            }
        }
        fun bind(pic: Picture){
            Glide.with(binding.pictureImage.context)
                .load(if(pic.path.isBlank()) pic.url else pic.path)
                .placeholder(R.drawable.jetpack_logo)
                //.transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.pictureImage)
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









