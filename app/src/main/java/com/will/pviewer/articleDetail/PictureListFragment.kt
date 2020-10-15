package com.will.pviewer.articleDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.will.pviewer.R
import com.will.pviewer.articleDetail.adapter.PictureAdapter
import com.will.pviewer.articleDetail.service.DOWNLOAD_SERVICE_ARTICLE_DATA
import com.will.pviewer.articleDetail.service.DownloadService
import com.will.pviewer.databinding.FragmentPictureListBinding
import com.will.pviewer.mainPage.viewModel.ArticleViewModel
import com.will.pviewer.articleDetail.viewModel.PictureListViewModel
import com.will.pviewer.data.AppDatabase
import com.will.pviewer.data.Article
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.Picture
import com.will.pviewer.network.PictureDownloader
import com.will.pviewer.network.PictureDownloadCallback
import com.will.pviewer.setting.LOG_TAG
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.File

/**
 * created  by will on 2020/9/18 17:57
 */
class PictureListFragment(): Fragment() {
    val viewModel: PictureListViewModel by activityViewModels()
    lateinit var article: ArticleWithPictures


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPictureListBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_picture_list,container,false)
        initializeView(binding)
        return binding.root
    }

    private fun initializeView(binding: FragmentPictureListBinding){
        setHasOptionsMenu(true)
        val toolbar = binding.fragmentPictureListToolbar
        val parent = requireActivity() as AppCompatActivity
        parent.setSupportActionBar(toolbar)
        parent.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener{
            parent.onBackPressed()
        }
        val list = binding.fragmentPictureListRecycler
        val adapter = PictureAdapter{
            viewModel.currentIndex.value = it
            val gallery = GalleryFragment()
            parentFragmentManager.beginTransaction().setCustomAnimations(R.anim.fragment_fade_enter,R.anim.fragment_fade_exit).add(R.id.activity_article_detail_container,gallery,null).addToBackStack(null).commit()
        }
        list.adapter = adapter
        viewModel.getArticle().observe(viewLifecycleOwner, Observer {
            article = it
            adapter.submitList(it.pictureList)
            binding.viewModel = ArticleViewModel(it)
        })
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_picture_list,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.picture_toolbar_download_article){
            val intent = Intent(requireContext(),DownloadService::class.java).apply {
                putExtra(DOWNLOAD_SERVICE_ARTICLE_DATA,article)
            }
            requireContext().startService(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
