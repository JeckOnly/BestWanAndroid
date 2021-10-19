package com.example.wanandroid.feature_homearticle.domain.use_case

import com.example.wanandroid.feature_homearticle.data.repository.MainRepository
import com.example.wanandroid.feature_homearticle.domain.model.banner.Banner
import kotlinx.coroutines.flow.Flow


/**
 *@className GetBannerUseCase
 *@Author xiejunyan
 *@Date 2021/10/6
 *@Description 用例——获取首页banner
 */
class GetBannerUseCase(
    private val repository: MainRepository
) {
    operator fun invoke(): Flow<List<Banner>>{
        return repository.getBanners()
    }
}