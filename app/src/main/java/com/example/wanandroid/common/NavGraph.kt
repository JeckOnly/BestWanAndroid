package com.example.wanandroid.common

import androidx.navigation.NavHostController

/**
 * Models the screens in the app and any arguments they require.
 */
object Destinations {
    const val HomeScreen = "HomeScreen"
    const val ArticleScreen = "ArticleScreen"
}

/**
 * Models the navigation actions in the app.
 */
class Actions(navController: NavHostController) {


    val toHomeScreen: () -> Unit = {
        navController.navigate(Destinations.HomeScreen){
            popUpTo(Destinations.HomeScreen) { inclusive = true }
        }
    }
}

