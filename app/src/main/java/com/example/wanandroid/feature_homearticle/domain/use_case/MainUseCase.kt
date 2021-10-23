package com.example.wanandroid.feature_homearticle.domain.use_case


/**
 *@className HArticleUseCase
 *@Author xiejunyan
 *@Date 2021/9/30
 *@Description 用于管理所有use_case的data class
 */
data class MainUseCase(
    val getArticlesUseCase: GetArticlesUseCase,
    val getBannerUseCase: GetBannerUseCase,
    val getTopArticlesUseCase: GetTopArticlesUseCase,
    val insertArticleCollectUseBase: InsertArticleCollectUseBase,
    val loadArticleCollectUseBase: LoadArticleCollectUseBase,
    val deleteArticleCollectUseCase: DeleteArticleCollectUseCase
)