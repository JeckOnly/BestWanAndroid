package com.example.wanandroid.feature_homearticle.domain.model.article_collect

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleCollect(
    val author: String,
    val shareUser: String,
    val title: String,
    val link: String,
    val chapterName: String,
    val superChapterName: String,
    val timestamp: Long = System.currentTimeMillis(),
    @PrimaryKey val id: Int
)

