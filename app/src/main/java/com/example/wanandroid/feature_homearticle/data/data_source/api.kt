package com.example.wanandroid.feature_homearticle.data.data_source

import com.example.wanandroid.feature_homearticle.domain.model.article.OutSide1
import com.example.wanandroid.feature_homearticle.domain.model.article_top.OutSide4
import com.example.wanandroid.feature_homearticle.domain.model.banner.OutSide3
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): OutSide1

    @GET("banner/json")
    suspend fun getBanner(): OutSide3

    @GET("article/top/json")
    suspend fun getTopArticles(): OutSide4
}