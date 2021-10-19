package com.example.wanandroid.feature_homearticle.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wanandroid.feature_homearticle.domain.model.article_collect.ArticleCollect


/**
 *@className ArticleCollectDatabase
 *@Author xiejunyan
 *@Date 2021/10/11
 *@Description Room数据库， 用于收藏article
 */
@Database(entities = [ArticleCollect::class], version = 1)
abstract class ArticleCollectDatabase :RoomDatabase(){
    abstract fun articleCollectDao(): ArticleCollectDao

    companion object{
        const val COLLECT_DATABASE_NAME = "ArticleCollectDatabase"
    }
}