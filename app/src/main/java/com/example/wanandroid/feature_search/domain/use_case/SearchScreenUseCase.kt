package com.example.wanandroid.feature_search.domain.use_case

import com.example.wanandroid.feature_homearticle.domain.use_case.InsertArticleCollectUseCase


/**
 *@className SearchScreenUseCase
 *@Author xiejunyan
 *@Date 2021/10/27
 *@Description 用于管理搜索页面上的所有use case的class
 */
data class SearchScreenUseCase (
    val searchUseCase: SearchUseCase,
    val insertArticleCollectUseCase: InsertArticleCollectUseCase
)