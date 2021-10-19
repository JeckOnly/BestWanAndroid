package com.example.wanandroid.feature_homearticle.data.data_source

import androidx.room.*
import com.example.wanandroid.feature_homearticle.domain.model.article_collect.ArticleCollect
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleCollectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticleCollect(articleCollect: ArticleCollect)

    @Delete
    suspend fun deleteArticleCollect(articleCollect: ArticleCollect)

    @Query("SELECT * FROM articlecollect order by timestamp DESC")
    fun loadArticleCollect(): Flow<List<ArticleCollect>>
}