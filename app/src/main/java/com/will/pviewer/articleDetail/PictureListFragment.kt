package com.will.pviewer.articleDetail

import android.os.Bundle
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
        }
        detailViewModel.article.observe(viewLifecycleOwner){
            adapter.submitList(it.pictureList)
            binding.fragmentPictureListToolbar.title = it.article.title

            //toolbar menu的加载顺序较为靠后，若此处同步执行会因为找不到指定menu而导致出错，故将其post到ui线程中延后执行
            toolbar.post{
                val exist = it.article.exist

                val favoriteItem = binding.fragmentPictureListToolbar.menu.findItem(R.id.picture_toolbar_like)
                favoriteItem.setIcon(if(exist) R.drawable.icon_favorited else R.drawable.icon_favorite)
                favoriteItem.isEnabled = !exist

                val deleteItem = binding.fragmentPictureListToolbar.menu.findItem(R.id.picture_toolbar_unlike)
                deleteItem.isVisible = exist
            }
        }

        detailViewModel.getArticle()
        list.adapter = adapter

    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_picture_list,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.picture_toolbar_like){
            val article = detailViewModel.article.value
            article?.let {
                item.isEnabled = false
                detailViewModel.downloadBinder.value?.getService()?.download(article)?.observe(viewLifecycleOwner){
                    if(it == DownloadService.STATUS_DOWNLOADED){
                        detailViewModel.setArticleExist(true)
                    }
                }
            }

        }else if(item.itemId == R.id.picture_toolbar_unlike){
            detailViewModel.deleteArticleFromLocal(requireContext())
        }
        return true
    }

}
