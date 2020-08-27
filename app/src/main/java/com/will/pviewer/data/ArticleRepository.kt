package com.will.pviewer.data

class ArticleRepository private constructor(private val dao: ArticleDao){


    fun insertArticle(article: Article) = dao.insertArticle(article)
    fun insertArticles(articles: List<Article>) = dao.insertArticles(articles)

    companion object{
        @Volatile private var instance: ArticleRepository? = null
        fun getInstance(dao: ArticleDao): ArticleRepository{
            return instance ?:synchronized(this){
                instance ?: ArticleRepository(dao).also { instance = it}
            }
        }

    }
}