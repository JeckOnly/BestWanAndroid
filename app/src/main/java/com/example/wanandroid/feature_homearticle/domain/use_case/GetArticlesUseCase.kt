package com.example.wanandroid.feature_homearticle.domain.use_case

import com.example.wanandroid.feature_homearticle.data.repository.MainRepository
import com.example.wanandroid.feature_homearticle.domain.model.article.Article
import kotlinx.coroutines.flow.Flow


/**
 *@className GetArticlesUseCase
 *@Author xiejunyan
 *@Date 2021/9/30
 *@Description 用例——获取首页文章
 */
class GetArticlesUseCase(
    private val repository: MainRepository
) {
    operator fun invoke(page: Int): Flow<List<Article>> {
        return repository.getArticles(page)
    }
}