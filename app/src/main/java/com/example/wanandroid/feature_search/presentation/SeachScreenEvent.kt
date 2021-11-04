package com.example.wanandroid.feature_search.presentation

import com.example.wanandroid.feature_homearticle.domain.model.article_collect.ArticleCollect


/**
 *@className SeachScreenEvents
 *@Author xiejunyan
 *@Date 2021/10/29
 *@Description 管理search screen上所有的event
 */
sealed class SearchScreenEvent {
    data class Search(val text: String): SearchScreenEvent()
    data class InsertArticleCollect(val articleCollect: ArticleCollect): SearchScreenEvent()

    /**
     * 用于上拉加载更多的搜索结果时的事件
     */
    object PageUp : SearchScreenEvent()
}