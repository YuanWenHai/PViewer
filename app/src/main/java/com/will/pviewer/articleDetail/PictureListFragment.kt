package com.will.pviewer.articleDetail

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.will.pviewer.R
import com.will.pviewer.articleDetail.adapter.PictureAdapter
import com.will.pviewer.databinding.FragmentPictureListBinding
import com.will.pviewer.viewmodels.ArticleViewModel
import com.will.pviewer.articleDetail.viewModel.PictureListViewModel
import com.will.pviewer.data.AppDatabase
import com.will.pviewer.data.Article
import com.will.pviewer.data.ArticleWithPictures
import com.will.pviewer.data.Picture
import com.will.pviewer.network.PictureDownloader
import com.will.pviewer.network.PictureDownloadCallback
import com.will.pviewer.setting.LOG_TAG
import io.reactivex.rxjava3.internal.schedulers.RxThreadFactory
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import java.io.File


private val Fragment.showStatusBar: Unit
    get() {
        TODO("Not yet implemented")
    }

/**
 * created  by will on 2020/9/18 17:57
 */
class PictureListFragment(): Fragment() {
    val viewModel: PictureListViewModel by activityViewModels()
    lateinit var article: ArticleWithPictures

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPictureListBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_picture_list,container,false)
        binding.fragmentPictureListToolbar.setOnMenuItemClickListener{
            if(it.itemId == R.id.picture_toolbar_download_article){
                saveOnSimpleThread(article,requireContext().applicationContext)
            }
            true
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
        return binding.root
    }

    private fun saveOnSimpleThread(articleWithPictures: ArticleWithPictures,context: Context){
        //TODO  这里将在后期更新为coroutine or rxjava
        Thread{
            save(articleWithPictures,context)
        }.start()
    }

    private fun save(articleWithPictures: ArticleWithPictures,context: Context){

        val count = AppDatabase.getInstance(requireContext()).articleDao().getArticleCountByUuid(articleWithPictures.article.uuid)
        if(count != 0){
            Log.w(LOG_TAG,"download cancel,article ${articleWithPictures.article.title} already exists")
            return
        }
        requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.let {
                rootDir ->
            val fileDir = File(rootDir,articleWithPictures.article.uuid)
            fileDir.mkdirs()
            PictureDownloader(fileDir,articleWithPictures,
                object: PictureDownloadCallback{
                override fun onResult(picture: Picture) {

                }

                override fun onError(picture: Picture) {

                }

                override fun onFinish(total: Int, succeed: Int,succeedList: List<Picture>) {
                    val article = Article(articleWithPictures.article,succeed,true)
                    AppDatabase.getInstance(context).apply {
                        articleDao().insertArticle(article)
                        pictureDao().insertPictures(succeedList)
                    }
                    val msg = "下载:${article.title} 到$fileDir ,共有图片${articleWithPictures.pictureList.size}张,下载成功$succeed 张"
                    Log.d(LOG_TAG,msg)
                }
            }).downloadPictures()
        }
    }

}
