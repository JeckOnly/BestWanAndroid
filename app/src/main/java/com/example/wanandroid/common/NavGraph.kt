package com.example.wanandroid.common

import androidx.navigation.NavHostController

/**
 * Models the screens in the app and any arguments they require.
 */
object Destinations {
    const val HomeScreen = "HomeScreen"//主页面
    const val SearchScreen = "SearchScreen"//搜索结果的页面
}

/**
 * Models the navigation actions in the app.
 */
class Actions(navController: NavHostController) {


    val toHomeScreen: () -> Unit = {
        navController.navigate(Destinations.HomeScreen){
            popUpTo(Destinations.HomeScreen) { inclusive = true }//回到主页面并且弹出栈的所有页面
        }
    }
    val toSearchScreen: () -> Unit = {
        navController.navigate(Destinations.SearchScreen)//前往搜索页面
    }
}

