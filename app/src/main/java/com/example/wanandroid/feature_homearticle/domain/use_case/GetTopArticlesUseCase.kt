package com.example.wanandroid.feature_homearticle.domain.use_case

import com.example.wanandroid.feature_homearticle.data.repository.MainRepository
import com.example.wanandroid.feature_homearticle.domain.model.article.Article
import kotlinx.coroutines.flow.Flow


/**
 *@className GetTopArticlesUseCase
 *@Author xiejunyan
 *@Date 2021/10/12
 *@Description 获取首页置顶文章的用例
 */
class GetTopArticlesUseCase (
    private val repository: MainRepository
) {
    operator fun invoke(): Flow<List<Article>> {
        return repository.getTopArticles()
    }
}