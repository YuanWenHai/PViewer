package com.will.pviewer.mainPage.adapter

import android.content.Intent
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.bitmap.DrawableTransformation
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.will.pviewer.articleDetail.ARTICLE_ACTIVITY_DATA
import com.will.pviewer.articleDetail.ArticleActivity
import com.will.pviewer.R
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.databinding.ItemArticleBinding
import com.will.pviewer.mainPage.viewModel.ArticleViewModel
import com.will.pviewer.util.Util

/**
 * created  by will on 2020/8/25 11:24
 */
class ArticleListAdapter: PagingDataAdapter<ArticleWithPictures, ArticleListAdapter.ViewHolder>(
    ArticleDiffCallback()
) {

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
                //防止重复点击，1s延迟
                Util.preventDoubleClick(it)
                val intent = Intent(binding.root.context, ArticleActivity::class.java)
                intent.putExtra(ARTICLE_ACTIVITY_DATA,binding.viewModel?.articleWithPictures)
                binding.itemArticleName.context.startActivity(intent)
            }
        }
        fun bind(articleWithPictures: ArticleWithPictures){
            with(binding){
                viewModel = ArticleViewModel(articleWithPictures)
                val picViews = listOf(binding.itemArticleThumb1,binding.itemArticleThumb2,binding.itemArticleThumb3)
                val picList = articleWithPictures.pictureList
                picViews.forEachIndexed{index, imageView ->

                    if(picList.size >= index+1){
                        imageView.visibility = View.VISIBLE
                            Glide.with(imageView).load(picList[index].url).placeholder(R.drawable.jetpack_logo).override(500,500)
                            .transition(DrawableTransitionOptions.withCrossFade()).into(imageView)
                    }else{
                        imageView.visibility = View.GONE
                    }

                }
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