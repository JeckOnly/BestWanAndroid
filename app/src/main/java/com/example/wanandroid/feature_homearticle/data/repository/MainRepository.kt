package com.example.wanandroid.feature_homearticle.data.repository

import android.content.Context
import androidx.room.Room
import com.example.wanandroid.feature_homearticle.data.data_source.Api
import com.example.wanandroid.feature_homearticle.data.data_source.ArticleCollectDao
import com.example.wanandroid.feature_homearticle.data.data_source.ArticleCollectDatabase
import com.example.wanandroid.feature_homearticle.domain.model.article.Article
import com.example.wanandroid.feature_homearticle.domain.model.article_collect.ArticleCollect
import com.example.wanandroid.feature_homearticle.domain.model.banner.Banner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 *@className HArticleRepository
 *@Author xiejunyan
 *@Date 2021/9/29
 *@Description 主页面文章列表数据源的仓库
 */
class MainRepository(private val dao: ArticleCollectDao) {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.wanandroid.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: Api = retrofit.create(Api::class.java)

    fun getArticles(page: Int): Flow<List<Article>>{
        return flow {
            val articles:List<Article> = service.getHomeArticles(page).data.datas
            emit(articles)
        }.flowOn(Dispatchers.IO)
    }

    fun getBanners():Flow<List<Banner>>{
        return flow {
            val banners:List<Banner> = service.getBanner().data
            emit(banners)
        }.flowOn(Dispatchers.IO)
    }

    fun getTopArticles(): Flow<List<Article>>{
        return flow {
            val articles:List<Article> = service.getTopArticles().data
            emit(articles)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun insertArticleCollect(articleCollect: ArticleCollect) = dao.insertArticleCollect(articleCollect)

    suspend fun deleteArticleCollect(articleCollect: ArticleCollect) = dao.deleteArticleCollect(articleCollect)

    fun loadArticleCollect() = dao.loadArticleCollect()
}