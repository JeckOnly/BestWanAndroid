package com.example.wanandroid.feature_search.presentation

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wanandroid.feature_homearticle.domain.model.article.Article
import com.example.wanandroid.feature_homearticle.domain.use_case.MainUseCase
import com.example.wanandroid.feature_search.domain.model.HotKey
import com.example.wanandroid.feature_search.domain.use_case.SearchScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


/**
 *@className SearchScreenViewModel
 *@Author xiejunyan
 *@Date 2021/10/27
 *@Description 用于搜索的界面的view model
 */
@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    @Named("SearchScreenUseCase") private val searchScreenUseCase: SearchScreenUseCase
) : ViewModel() {
    @InternalCoroutinesApi
    private var textTrigger = ""
        set(value) {
            field = value
            page = 0
            viewModelScope.launch {
                searchScreenUseCase.searchUseCase(page, value).collect {
                    searchResult.clear()
                    searchResult.addAll(it)
                }
            }
        }
    private var page = 0

    val searchResult: MutableList<Article> = mutableStateListOf()

    private var _hotKeys: MutableState<List<HotKey>> = mutableStateOf(emptyList())
    val hotKeys get() = _hotKeys

    init {
        viewModelScope.launch {
            _hotKeys.value = searchScreenUseCase.getHotKeyUseCase().plus(//获取api的数据加上自己造的一些热词
                arrayOf(
                    HotKey(name = "Jetpack compose"),
                    HotKey(name = "kotlin"),
                    HotKey(name = "Java"),
                    HotKey(name = "Android 12")
                )
            )
        }
    }

    /**
     * 处理收到的各种界面传来的事件
     */
    @InternalCoroutinesApi
    fun onEvents(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.Search -> {//搜索
                textTrigger = event.text
            }
            is SearchScreenEvent.InsertArticleCollect -> {//收藏
                viewModelScope.launch {
                    searchScreenUseCase.insertArticleCollectUseCase(event.articleCollect)
                }
            }
            is SearchScreenEvent.PageUp -> {//加载更多文章
                page++
                Log.d("SearchScreenViewModel", "$page")
                viewModelScope.launch {
                    searchScreenUseCase.searchUseCase(page, textTrigger).collect {
                        searchResult.addAll(it)
                    }
                }
            }
        }
    }
}