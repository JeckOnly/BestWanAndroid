package com.example.wanandroid.feature_homearticle.presentation.homearticles.components

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wanandroid.feature_homearticle.domain.model.article.Article
import com.example.wanandroid.feature_homearticle.domain.model.article_collect.ArticleCollect
import com.example.wanandroid.feature_homearticle.domain.model.banner.Banner
import com.example.wanandroid.feature_homearticle.domain.use_case.GetArticlesUseCase
import com.example.wanandroid.feature_homearticle.domain.use_case.GetBannerUseCase
import com.example.wanandroid.feature_homearticle.domain.use_case.MainUseCase
import com.example.wanandroid.feature_homearticle.presentation.util.ArticlesEvent
import com.example.wanandroid.feature_homearticle.presentation.util.ArticlesState
import com.example.wanandroid.feature_homearticle.presentation.util.BottomBarScreen
import com.example.wanandroid.feature_homearticle.presentation.util.Direction
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


/**
 *@className HArticleViewModel
 *@Author xiejunyan
 *@Date 2021/9/29
 *@Description 显示主页面 文章 的view model
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    @Named("MainUseCase") private val mainUseCase: MainUseCase
) : ViewModel() {
    private val _state = mutableStateOf(ArticlesState())//隐藏
    val state: State<ArticlesState> get() = _state//暴露

    private var _bannerState: List<Banner> by mutableStateOf(emptyList())//隐藏
    val bannerState: List<Banner> get() = _bannerState//暴露

    private var _topArticle: List<Article> by mutableStateOf(emptyList())//隐藏
    val topArticle: List<Article> get() = _topArticle//暴露

    private var _bottomBarScreen: BottomBarScreen by mutableStateOf(BottomBarScreen.HomeScreen)//隐藏
    val bottomBarScreen: BottomBarScreen get() = _bottomBarScreen//暴露

    val articleCollect: Flow<List<ArticleCollect>> = mainUseCase.loadArticleCollectUseBase()
    private var direction = Direction.Right

    //创建的时候加载第一页的文章、banner、置顶文章
    init {
        viewModelScope.launch {
            var newArticles = emptyList<Article>()
            mainUseCase.getArticlesUseCase(0).collect {//加载首页文章
                newArticles = it
            }
            _state.value = _state.value.copy(articles = newArticles)
            mainUseCase.getBannerUseCase().collect {//加载首页banner
                _bannerState = it
            }
            mainUseCase.getTopArticlesUseCase().collect { //加载置顶文章
                _topArticle = it
            }
        }
    }

    //处理各种viewmodel会面对的事件
    @ExperimentalCoroutinesApi
    fun onEvent(event: ArticlesEvent) {
        when (event) {
            is ArticlesEvent.UpOrDownPage -> {
                if (event.upOrDown == 1) {
                    direction = Direction.Right
                    pageChanged(1)
                } else {
                    direction = Direction.Left
                    pageChanged(-1)
                }
            }
            is ArticlesEvent.InsertArticleCollect -> {
                viewModelScope.launch {
                    mainUseCase.insertArticleCollectUseBase(event.articleCollect)//把文章插入收藏的数据库
                }
            }
            is ArticlesEvent.DeleteArticleCollect -> {
                viewModelScope.launch(start = CoroutineStart.ATOMIC) {
                    mainUseCase.deleteArticleCollectUseCase(event.articleCollect)//删除收藏的文章
                }
            }
            is ArticlesEvent.ClickBottomBar -> {
               if(event.bottomBarScreen != _bottomBarScreen) _bottomBarScreen = event.bottomBarScreen//不等于就改变
            }
        }
    }

    //传入1表示页码前进1， 传入-1表示页码倒退1
    private fun pageChanged(upOrDown: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val newPage = if (upOrDown == 1) _state.value.page + 1 else _state.value.page - 1
            var newArticles = emptyList<Article>()
            mainUseCase.getArticlesUseCase(newPage).collect {
                newArticles = it
            }
            _state.value =
                _state.value.copy(articles = newArticles, page = newPage, loading = false)
        }
    }

    fun getDirection(): Direction = direction
}