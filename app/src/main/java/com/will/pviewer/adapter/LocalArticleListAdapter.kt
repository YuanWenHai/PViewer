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
import com.will.pviewer.databinding.ItemArticleBinding
import com.will.pviewer.databinding.ItemArticlePictureBinding
import com.will.pviewer.viewmodels.ArticleViewModel

/**
 * created  by will on 2020/9/11 14:41
 */
class LocalArticleListAdapter: PagingDataAdapter<ArticleWithPictures,LocalArticleListAdapter.ViewHolder>(
    diffCallback) {



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_article,parent,false))
    }

    class ViewHolder(private val binding: ItemArticleBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(article: ArticleWithPictures){
            with(binding){
                viewModel = ArticleViewModel(article)
                executePendingBindings()
            }
        }
    }

    companion object{

        private val diffCallback = object: DiffUtil.ItemCallback<ArticleWithPictures>(){
            override fun areItemsTheSame(
                oldItem: ArticleWithPictures,
                newItem: ArticleWithPictures
            ) = oldItem.article.id == newItem.article.id

            override fun areContentsTheSame(
                oldItem: ArticleWithPictures,
                newItem: ArticleWithPictures
            ) = oldItem.article == newItem.article
        }

    }

}