package com.example.wanandroid.feature_homearticle.domain.model.article

import com.example.wanandroid.feature_homearticle.domain.model.article_collect.ArticleCollect


/**
 * @Description 首页文章的模型类
 */
data class Article(
    val author: String,
    val shareUser: String,
    var title: String,
    val niceDate: String,
    val link: String,
    val chapterName: String,
    val superChapterName: String,
    val id: Int
) {
    /**
     * 把一个Article转换为ArticleCollect
     */
    fun toArticleCollect() = ArticleCollect(
            author=author,
            shareUser=shareUser,
            title=title,
            link=link,
            chapterName=chapterName,
            superChapterName=superChapterName,
            id=id
        )
}
