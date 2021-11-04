package com.example.wanandroid.feature_search.domain.use_case

import com.example.wanandroid.feature_homearticle.data.repository.MainRepository
import com.example.wanandroid.feature_homearticle.domain.model.article.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch


/**
 *@className SearchUseCase
 *@Author xiejunyan
 *@Date 2021/10/27
 *@Description 用于搜索的use case
 */
class SearchUseCase (
    private val repository: MainRepository
) {
    operator fun invoke(page: Int, key: String): Flow<List<Article>> {
        return repository.search(page, key)
    }
}