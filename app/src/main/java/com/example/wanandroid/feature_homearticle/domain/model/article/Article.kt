package com.example.wanandroid.feature_homearticle.domain.model.article


/**
 * @Description 首页文章的模型类
 */
data class Article(
    val author: String,
    val shareUser: String,
    val title: String,
    val niceDate: String,
    val link: String,
    val chapterName: String,
    val superChapterName: String,
    val id: Int
)
