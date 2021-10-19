package com.example.wanandroid.feature_homearticle.presentation.homearticles.components

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.example.wanandroid.R
import com.example.wanandroid.feature_homearticle.presentation.util.BottomBarScreen
import com.example.wanandroid.feature_homearticle.presentation.util.columnSlideAnim
import com.example.wanandroid.feature_homearticle.presentation.util.whenDragColumn
import com.example.wanandroid.ui.theme.CollectScreenBackColor
import com.example.wanandroid.ui.theme.HomeScreenBackColor
import com.google.accompanist.pager.ExperimentalPagerApi


/**
 *@className MainScreen
 *@Author xiejunyan
 *@Date 2021/9/29
 *@Description 显示主页面文章的 main screen
 */
private const val TAG = "MainScreen.kt"

@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    var topArticleFold: Boolean by remember { mutableStateOf(true) }
    val animateBackCol by //背景颜色的动画改变
    animateColorAsState(
        if (viewModel.bottomBarScreen is BottomBarScreen.HomeScreen) HomeScreenBackColor else CollectScreenBackColor,
        animationSpec = TweenSpec(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        )
    )
    val articlesLazyColumnState = rememberLazyListState()

    Scaffold(
        backgroundColor = animateBackCol,
        bottomBar = {
            AnimateAppBottomBar(articlesLazyColumnState, animateBackCol)
        }
    ) {
        Column {
            if (viewModel.bottomBarScreen == BottomBarScreen.HomeScreen) {
                HomeScreen(topArticleFold, viewModel, articlesLazyColumnState) {
                    topArticleFold = !topArticleFold
                }
            } else if (viewModel.bottomBarScreen == BottomBarScreen.CollectScreen) {
                CollectScreen()
            }
        }
    }
}


