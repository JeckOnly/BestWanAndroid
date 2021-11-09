package com.example.wanandroid.feature_homearticle.data.data_source

import com.example.wanandroid.feature_homearticle.domain.model.article.OutSide1
import com.example.wanandroid.feature_homearticle.domain.model.article_top.OutSide4
import com.example.wanandroid.feature_homearticle.domain.model.banner.OutSide3
import com.example.wanandroid.feature_search.domain.model.HotKeyOutSide
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): OutSide1

    @GET("banner/json")
    suspend fun getBanner(): OutSide3

    @GET("article/top/json")
    suspend fun getTopArticles(): OutSide4

    /**
     * @param page 当前查询的页码
     * @param key 用于查询的关键字
     */
    @POST("article/query/{page}/json")
    suspend fun search(@Path("page") page: Int, @Query("k") key: String): OutSide1

    /**
     * 查询搜索热词
     */
    @GET("hotkey/json")
    suspend fun getHotKey(): HotKeyOutSide
}