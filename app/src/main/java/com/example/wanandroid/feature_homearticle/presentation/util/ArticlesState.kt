package com.example.wanandroid.feature_homearticle.presentation.util

import com.example.wanandroid.feature_homearticle.domain.model.article.Article

/**
 * @Description 用来表示主页面文章列表的状态
 */
data class ArticlesState(
    val articles: List<Article> = emptyList(),
    val page: Int = 0,
    val loading:Boolean = false, //当时想用来：加载列表项的时候显示一个加载的动画，可是发现加载速度还是挺快的，动画也看不见
)
