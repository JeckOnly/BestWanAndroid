package com.example.wanandroid.feature_homearticle.presentation.util

import androidx.compose.ui.graphics.Color
import com.example.wanandroid.R
import com.example.wanandroid.ui.theme.CollectScreenBackColor
import com.example.wanandroid.ui.theme.CollectScreenBottomBar
import com.example.wanandroid.ui.theme.HomeScreenBackColor
import com.example.wanandroid.ui.theme.HomeScreenBottomBar

//底部导航栏的item种类
sealed class BottomBarScreen(val id: Int, val color: Color){
    object HomeScreen: BottomBarScreen(R.drawable.ic_house, HomeScreenBottomBar)
    object CollectScreen: BottomBarScreen(R.drawable.ic_star, CollectScreenBottomBar)
}