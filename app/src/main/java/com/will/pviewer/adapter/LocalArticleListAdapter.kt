package com.will.pviewer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.will.pviewer.R
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.Picture
import com.will.pviewer.databinding.ItemArticlePictureBinding

/**
 * created  by will on 2020/9/11 14:41
 */
class LocalArticleListAdapter: PagingDataAdapter<Picture,LocalArticleListAdapter.ViewHolder>(
    diffCallback) {



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_article_picture,parent,false))
    }

    class ViewHolder(private val binding: ItemArticlePictureBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(pic: Picture){
            Glide.with(binding.pictureImage.context).load(pic.url).into(binding.pictureImage)
        }
    }

    companion object{

        private val diffCallback = object: DiffUtil.ItemCallback<Picture>(){
            override fun areItemsTheSame(
                oldItem: Picture,
                newItem: Picture
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Picture,
                newItem: Picture
            ) = oldItem == newItem
        }

    }

}