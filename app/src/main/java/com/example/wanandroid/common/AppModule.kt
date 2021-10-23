package com.example.wanandroid.common

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.wanandroid.feature_homearticle.data.data_source.ArticleCollectDatabase
import com.example.wanandroid.feature_homearticle.data.data_source.ArticleCollectDatabase.Companion.COLLECT_DATABASE_NAME
import com.example.wanandroid.feature_homearticle.data.repository.MainRepository
import com.example.wanandroid.feature_homearticle.domain.use_case.*
import com.example.wanandroid.feature_homearticle.presentation.util.ArticlesEvent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 *
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * 依赖注入创建collect database
     */
    @Singleton
    @Provides
    @Named("ArticleCollectDatabase")
    fun provideCollectDatabase(@ApplicationContext context: Context): ArticleCollectDatabase{
        return Room.databaseBuilder(
            context,
            ArticleCollectDatabase::class.java,
            COLLECT_DATABASE_NAME
        ).build()
    }

    /**
     * 依赖注入创建main repository
     */
    @Singleton
    @Provides
    @Named("MainRepository")
    fun provideMainRepository(@Named("ArticleCollectDatabase") db: ArticleCollectDatabase): MainRepository{
        return MainRepository(db.articleCollectDao())
    }

    /**
     * 依赖注入创建管理所有use case的那个类
     */
    @Singleton
    @Provides
    @Named("MainUseCase")
    fun provideMainUseCase(@Named("MainRepository") repo: MainRepository): MainUseCase{
        return MainUseCase(
            GetArticlesUseCase(repo),
            GetBannerUseCase(repo),
            GetTopArticlesUseCase(repo),
            InsertArticleCollectUseBase(repo),
            LoadArticleCollectUseBase(repo),
            DeleteArticleCollectUseCase(repo)
        )
    }
}