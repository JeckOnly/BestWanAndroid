package com.example.wanandroid.feature_homearticle.presentation.util

import com.example.wanandroid.feature_homearticle.domain.model.article_collect.ArticleCollect


/**
 *@className ArticlesEvent
 *@Author xiejunyan
 *@Date 2021/9/30
 *@Description 用来管理viewmodel会接收到的各种事件(事件不应该知道viewmodel的状态)
 */
sealed class ArticlesEvent {
    data class UpOrDownPage(val upOrDown: Int): ArticlesEvent()
    data class InsertArticleCollect(val articleCollect: ArticleCollect):ArticlesEvent()
    data class DeleteArticleCollect(val articleCollect: ArticleCollect):ArticlesEvent()
    data class ClickBottomBar(val bottomBarScreen: BottomBarScreen):ArticlesEvent()
}