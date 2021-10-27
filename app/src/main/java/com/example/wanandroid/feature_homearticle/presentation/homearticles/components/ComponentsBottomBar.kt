package com.example.wanandroid.feature_homearticle.presentation.homearticles.components

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wanandroid.R
import com.example.wanandroid.feature_homearticle.presentation.util.ArticlesEvent
import com.example.wanandroid.feature_homearticle.presentation.util.BottomBarScreen
import com.example.wanandroid.ui.theme.BOTTOM_ICON_SIZE
import com.example.wanandroid.ui.theme.COLOR_SELECTED


/**
 *@Author xiejunyan
 *@Date 2021/10/14
 *@Description 底部导航栏的组件
 */

@ExperimentalAnimationApi
@Composable
fun AnimateAppBottomBar(
    visibility: Boolean,
    animateBackCol: Color
) {
    AnimatedVisibility(
        visible = visibility,
        enter = slideInVertically(
            initialOffsetY = {
                it
            },
            animationSpec = TweenSpec(500)
        ),
        exit = slideOutVertically(
            targetOffsetY = {
                it
            },
            animationSpec = TweenSpec(500)
        )
    ) {
        AppBottomBar(animateBackCol)//底部栏
    }
}

@Composable
fun AppBottomBar(color: Color) {
    MyBottomBar(
        listOf(BottomBarScreen.HomeScreen, BottomBarScreen.CollectScreen),
        Modifier
            .fillMaxWidth()
            .background(color)
            .padding(top = 4.dp, bottom = 4.dp)
            .height(BOTTOM_ICON_SIZE * 1.5f)
    )
}

/**
 * @param screens 传入BottomBarScreen的对象列表
 * @param modifier 间接作用于Row（icon的大小已经在内部写好）
 */
@Composable
fun MyBottomBar(screens: List<BottomBarScreen>, modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = hiltViewModel()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { screen ->
            AnimatableIcon(
                resId = screen.id,
                iconSize = BOTTOM_ICON_SIZE,
                scale = if (viewModel.bottomBarScreen == screen) 1.5f else 1f,
                color = if (viewModel.bottomBarScreen == screen) COLOR_SELECTED else screen.color
            ) {
                viewModel.onEvent(ArticlesEvent.ClickBottomBar(screen))
            }
        }
    }
}

@Composable
fun AnimatableIcon(
    resId: Int,
    iconSize: Dp,
    scale: Float,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    // Animation params
    val animatedScale: Float by animateFloatAsState(
        targetValue = scale,
        // Here the animation spec serves no purpose but to demonstrate in slow speed.
        animationSpec = TweenSpec(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        )
    )
    val animatedColor by animateColorAsState(
        targetValue = color,
        animationSpec = TweenSpec(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        )
    )

    IconButton(
        onClick = onClick,
        modifier = modifier.size(iconSize)
    ) {
        Icon(
            painter = painterResource(id = resId),
            contentDescription = "icon",
            tint = animatedColor,
            modifier = modifier.scale(animatedScale)
        )
    }
}


//改变bar， screen的背景颜色也要改变
//fade in fade out
@Preview
@Composable
fun PreviewBottomBar() {

}
