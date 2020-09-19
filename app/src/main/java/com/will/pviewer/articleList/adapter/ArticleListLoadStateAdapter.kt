package com.will.pviewer.articleList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.will.pviewer.R
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.databinding.ItemNetworkStateBinding

/**
 * created  by will on 2020/9/18 11:06
 */
class ArticleListLoadStateAdapter(private val adapter: PagingDataAdapter<ArticleWithPictures, out RecyclerView.ViewHolder>):LoadStateAdapter<NetworkStateItemViewHolder>(){

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder {
        return NetworkStateItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_network_state,parent,false)){adapter.retry()}
    }
}
class NetworkStateItemViewHolder(private val binding: ItemNetworkStateBinding,private val retryCallback: ()-> Unit): RecyclerView.ViewHolder(
binding.root){
    init {
        binding.itemNetworkStateRetry.setOnClickListener{
            retryCallback()
        }
    }
    fun bindTo(loadState: LoadState) {
        binding.itemNetworkStateProgress.isVisible = loadState is LoadState.Loading
        binding.itemNetworkStateRetry.isVisible = loadState is LoadState.Error
        binding.itemNetworkStateMsg.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        binding.itemNetworkStateMsg.text = (loadState as? LoadState.Error)?.error?.message
    }
}

