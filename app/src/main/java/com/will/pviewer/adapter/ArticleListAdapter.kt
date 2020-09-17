package com.will.pviewer.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.will.pviewer.ARTICLE_ACTIVITY_DATA
import com.will.pviewer.ArticleActivity
import com.will.pviewer.R
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.databinding.ItemArticleBinding
import com.will.pviewer.viewmodels.ArticleViewModel

/**
 * created  by will on 2020/8/25 11:24
 */
class ArticleListAdapter: PagingDataAdapter<ArticleWithPictures,ArticleListAdapter.ViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_article,parent,false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class ViewHolder(private val binding: ItemArticleBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.setClickListener {
                val intent = Intent(binding.root.context,ArticleActivity::class.java)
                intent.putExtra(ARTICLE_ACTIVITY_DATA,binding.viewModel.articleWithPictures)
                binding.itemArticleName.context.startActivity(intent)
            }
        }
        fun bind(articleWithPictures: ArticleWithPictures){
            with(binding){
                viewModel = ArticleViewModel(articleWithPictures)
                executePendingBindings()
            }
        }
    }

}
private class ArticleDiffCallback: DiffUtil.ItemCallback<ArticleWithPictures> (){

    override fun areItemsTheSame(
        oldItem: ArticleWithPictures,
        newItem: ArticleWithPictures
    ): Boolean {
        return oldItem.article.id == newItem.article.id
    }

    override fun areContentsTheSame(
        oldItem: ArticleWithPictures,
        newItem: ArticleWithPictures
    ): Boolean {
        return oldItem.article.id == newItem.article.id
    }
}