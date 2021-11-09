package com.example.wanandroid.feature_search.domain.use_case

import com.example.wanandroid.feature_homearticle.data.repository.MainRepository
import com.example.wanandroid.feature_homearticle.domain.model.article.Article
import kotlinx.coroutines.flow.Flow


/**
 *@className GetHotKeyUseCase
 *@Author xiejunyan
 *@Date 2021/11/4
 *@Description 用于搜索热词
 */
class GetHotKeyUseCase(
    private val repository: MainRepository
) {
    suspend operator fun invoke() = repository.getHotKey()
}