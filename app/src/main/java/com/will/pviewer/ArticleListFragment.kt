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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.will.pviewer.adapter.ArticleListAdapter
import com.will.pviewer.adapter.LocalArticleListAdapter
import com.will.pviewer.data.*
import com.will.pviewer.databinding.FragmentArticleListBinding
import com.will.pviewer.network.ArticleService
import com.will.pviewer.viewmodels.ArticleListViewModel
import com.will.pviewer.viewmodels.ArticleListViewModelFactory
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

/**
 * created  by will on 2020/8/23 16:29
 */
class ArticleListFragment private  constructor(): Fragment() {


    private val viewModel: ArticleListViewModel by viewModels{
        ArticleListViewModelFactory(ArticleWithPicturesRepository.getInstance(AppDatabase.getInstance(requireContext()).articleWithPicturesDao()))
    }

    private fun getAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder>{
        return when(getType(this)){
            TYPE_SELFIE -> ArticleListAdapter()
            TYPE_POST -> ArticleListAdapter()
            TYPE_FAVORITE -> LocalArticleListAdapter()
            else -> ArticleListAdapter()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentArticleListBinding.inflate(inflater,container,false)
        val adapter = getAdapter()
        binding.fragmentArticleListRecycler.adapter = adapter
        binding.fragmentArticleListButton.setOnClickListener{
            Thread{makeFakeData()}.start()
        }
        lifecycleScope.launch {
            ArticleService.getInstance(requireContext()).getArticleList(1,1)
        }
        subscribeUi(adapter)
        return binding.root
    }
    private fun subscribeUi(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>){
        when(adapter){
             is ArticleListAdapter -> {
                 viewModel.articleWithPictures.observe(viewLifecycleOwner){result ->
                     adapter.submitList(result)
                 }
             }
             is LocalArticleListAdapter -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.articleWithPicturesPaging.collectLatest {
                        Log.e("submit data",it.toString())
                        adapter.submitData(it)
                    }
                }
            }
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
        const val TYPE_SELFIE = 0
        const val TYPE_POST = 1
        const val TYPE_FAVORITE = 2
        private const val TYPE = "fragment_type"

        fun getInstance(type: Int): Fragment {
            val instance = ArticleListFragment()
            val bundle = Bundle().apply {
                putInt(TYPE,type)
            }
            instance.arguments = bundle
            return instance
        }
        private fun getType(fragment: Fragment): Int{
             return fragment.requireArguments().getInt(TYPE,0)
        }
    }
}