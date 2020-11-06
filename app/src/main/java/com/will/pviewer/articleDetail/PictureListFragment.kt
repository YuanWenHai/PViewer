package com.will.pviewer.articleDetail

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.will.pviewer.R
import com.will.pviewer.articleDetail.adapter.PictureAdapter
import com.will.pviewer.articleDetail.service.DownloadService
import com.will.pviewer.articleDetail.viewModel.DetailViewModel
import com.will.pviewer.databinding.FragmentPictureListBinding
import com.will.pviewer.extension.withAnimation
import com.will.pviewer.setting.LOG_TAG
import com.will.pviewer.util.Util

/**
 * created  by will on 2020/9/18 17:57
 */
class PictureListFragment(): Fragment() {
    //val viewModel: PictureListViewModel by activityViewModels()
    //lateinit var article: ArticleWithPictures
    private val detailViewModel: DetailViewModel by activityViewModels()

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
            detailViewModel.currentIndex.value = it
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_top,
                    R.anim.nav_default_pop_exit_anim,
                    R.anim.nav_default_pop_enter_anim,
                    R.anim.nav_default_pop_exit_anim)
                .replace(R.id.detail_container,GalleryFragment())
                .addToBackStack(null)
                .setReorderingAllowed(true)
                .commit()
            //val gallery = GalleryFragment()
            //parentFragmentManager.beginTransaction().setCustomAnimations(R.anim.fragment_fade_enter,R.anim.fragment_fade_exit).add(R.id.activity_article_detail_container,gallery,null).addToBackStack(null).commit()
        }
        detailViewModel.getArticle().observe(viewLifecycleOwner){
            adapter.submitList(it.pictureList)
            binding.fragmentPictureListToolbar.title = it.article.title
        }
        list.adapter = adapter

    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_picture_list,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.picture_toolbar_download_article){
            val article = detailViewModel.getArticle().value!!
            if(article.article.exist){
                Util.makeToast(requireContext(),"The article is already downloaded")
                return true
            }
            val externalAvailable = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
            if(externalAvailable){
                val downloadDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                if(downloadDir != null){
                    val intent = DownloadService.buildIntent(requireContext(),article, downloadDir)
                    requireContext().startService(intent)
                }else{
                    Log.e(LOG_TAG,"cancel download,external storage directory is null!")
                    Util.makeToast(requireContext(),"cannot open file system,download canceled")
                }
            }else{
                Util.makeToast(requireContext(),"cannot open file system,download canceled")
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
