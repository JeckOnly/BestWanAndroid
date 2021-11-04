package com.example.wanandroid.feature_homearticle.domain.use_case

import com.example.wanandroid.feature_homearticle.data.repository.MainRepository
import com.example.wanandroid.feature_homearticle.domain.model.article.Article
import kotlinx.coroutines.flow.Flow


/**
 *@className LoadArticleCollectUseBase
 *@Author xiejunyan
 *@Date 2021/10/14
 *@Description 用于加载collect article的use base
 */
class LoadArticleCollectUseCase (
    private val repository: MainRepository
) {
    operator fun invoke() = repository.loadArticleCollect()
}