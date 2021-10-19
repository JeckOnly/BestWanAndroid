package com.example.wanandroid.feature_homearticle.domain.use_case

import com.example.wanandroid.feature_homearticle.data.repository.MainRepository
import com.example.wanandroid.feature_homearticle.domain.model.article.Article
import com.example.wanandroid.feature_homearticle.domain.model.article_collect.ArticleCollect
import kotlinx.coroutines.flow.Flow


/**
 *@className InsertArticleCollectUseBase
 *@Author xiejunyan
 *@Date 2021/10/14
 *@Description 用于插入收藏文章的use case
 */
class InsertArticleCollectUseBase (
    private val repository: MainRepository
) {
    suspend operator fun invoke(articleCollect: ArticleCollect){
        repository.insertArticleCollect(articleCollect)
    }
}