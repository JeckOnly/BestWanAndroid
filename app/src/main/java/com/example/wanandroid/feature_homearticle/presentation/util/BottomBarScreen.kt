package com.example.wanandroid.feature_homearticle.presentation.util

import androidx.compose.ui.graphics.Color
import com.example.wanandroid.R
import com.example.wanandroid.ui.theme.CollectScreenBottomBar
import com.example.wanandroid.ui.theme.HomeScreenBottomBar

//底部导航栏的item种类
sealed class BottomBarScreen(val id: Int, val name:String, val color: Color){
    object HomeScreen: BottomBarScreen(R.drawable.ic_house, "主页", HomeScreenBottomBar)
    object CollectScreen: BottomBarScreen(R.drawable.ic_collect, "收藏", CollectScreenBottomBar)
}