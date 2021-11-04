package com.example.wanandroid.feature_search.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wanandroid.R
import com.example.wanandroid.feature_homearticle.presentation.util.LottieLoadingView
import com.example.wanandroid.feature_homearticle.presentation.util.openLinkBrowser
import com.example.wanandroid.feature_search.domain.util.isScrolledToTheEnd
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch


/**
 *@Author xiejunyan
 *@Date 2021/10/27
 *@Description 用于搜索的界面
 */
private const val TAG = "SearchScreen"


@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@InternalCoroutinesApi
@Composable
fun SearchScreen(onClickBackIcon: () -> Unit) {
    val viewModel: SearchScreenViewModel = hiltViewModel()
    val listState = rememberLazyListState()
    val context = LocalContext.current
    // Creates a CoroutineScope bound to the SearchScreen's lifecycle
    val scope = rememberCoroutineScope()
    var timeMillis = remember { 0L }
    var loadAnim by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SearchScreenTopBar(
                modifier = Modifier.height(45.dp),
                onClickSearchText = {
                    viewModel.onEvents(SearchScreenEvent.Search(it)) //send the search event to the view model
                    scope.launch {
                        listState.scrollToItem(0)//scroll to the top of the list
                    }
                },
                onClickBackIcon = { onClickBackIcon() })//返回home screen
        }
    ) {

        if (listState.isScrollInProgress) {
            if (listState.isScrolledToTheEnd()) {
                loadAnim = true
                if (System.currentTimeMillis() - timeMillis > 4000) {//如果已经到了列表末尾， 并且还有滚动操作，而且距离上一次加载已经过去了4秒以上， 就加载更多
                    viewModel.onEvents(SearchScreenEvent.PageUp)
                    timeMillis = System.currentTimeMillis()
                }
            } else {
                loadAnim = false
            }
        }

        LazyColumn(state = listState) {
            items(items = viewModel.searchResult) {
                Divider(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                    color = Color.Gray.copy(alpha = 0.8f),
                    thickness = 0.8.dp,
                )
                SearchScreenArticle(
                    article = it,onClickItem = { openLinkBrowser(context, it.link)} ,onclickCollect = {}, modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                )
            }
            item {
                AnimatedVisibility(visible = loadAnim) {
                    Column {
                        Divider(
                            modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                            color = Color.Gray.copy(alpha = 0.8f),
                            thickness = 0.8.dp,
                        )
                        Box(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            LottieLoadingView(id = R.raw.search_articles_loading, modifier = Modifier.size(100.dp))
                        }
                    }
                }
            }
        }
    }
}

