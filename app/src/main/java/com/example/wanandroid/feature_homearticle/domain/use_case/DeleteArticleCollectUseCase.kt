package com.example.wanandroid.feature_homearticle.domain.use_case

import com.example.wanandroid.feature_homearticle.data.repository.MainRepository
import com.example.wanandroid.feature_homearticle.domain.model.article.Article
import com.example.wanandroid.feature_homearticle.domain.model.article_collect.ArticleCollect
import kotlinx.coroutines.flow.Flow


/**
 *@className DeleteArticleCollectUseCase
 *@Author xiejunyan
 *@Date 2021/10/20
 *@Description 用例——删除收藏文章
 */
class DeleteArticleCollectUseCase (
    private val repository: MainRepository
) {
    suspend operator fun invoke(articleCollect: ArticleCollect){
        repository.deleteArticleCollect(articleCollect)
    }
}