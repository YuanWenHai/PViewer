package com.will.pviewer

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.will.pviewer.adapter.ArticleListAdapter
import com.will.pviewer.data.*
import com.will.pviewer.databinding.FragmentArticleListBinding
import com.will.pviewer.viewmodels.ArticleListViewModel
import com.will.pviewer.viewmodels.ArticleListViewModelFactory
import java.util.*
import kotlin.collections.ArrayList

/**
 * created  by will on 2020/8/23 16:29
 */
class ArticleListFragment(val type: Int): Fragment() {

    private val viewModel: ArticleListViewModel by viewModels{
        ArticleListViewModelFactory(ArticleWithPicturesRepository.getInstance(AppDatabase.getInstance(requireContext()).articleWithPicturesDao()))
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentArticleListBinding.inflate(inflater,container,false)
        val adapter = ArticleListAdapter()
        binding.fragmentArticleListRecycler.adapter = adapter
        binding.fragmentArticleListButton.setOnClickListener{
            Thread{makeFakeData()}.start()
        }
        subscribeUi(adapter)
        return binding.root
    }
    private fun subscribeUi(adapter: ArticleListAdapter){
        viewModel.articleWithPictures.observe(viewLifecycleOwner){result ->
            adapter.submitList(result)
        }
    }
    private fun makeFakeData(){
        val db = AppDatabase.getInstance(requireContext())
        if(db.articleDao().getArticleCount() >= 5){
            return
        }
        val id: Int = Date().time.toInt()
        val article = Article(id,"Test Article: $id","Will","2020年8月27日19:59:28",5,"link: $id")
        val pictures: ArrayList<Picture> = ArrayList()
        for(i in 0..5){
            pictures.add(Picture(id-i,id,"fake",131,"testpic:${i}","url:$id"))
        }
        ArticleRepository.getInstance(db.articleDao()).insertArticle(article)
        PictureRepository.getInstance(db.pictureDao()).insertPictures(pictures)
        requireActivity().runOnUiThread{Toast.makeText(context,"added",Toast.LENGTH_SHORT).show()}

    }
    companion object{
        val TYPE_SELFIE = 0
        val TYPE_POST = 1
        val TYPE_FAVORITE = 2
    }
}