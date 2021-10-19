package com.example.wanandroid.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.wanandroid.feature_homearticle.presentation.homearticles.components.MainScreen
import com.google.accompanist.pager.ExperimentalPagerApi


/**
 *@Author xiejunyan
 *@Date 2021/10/2
 *@Description 掌管整个app的navigation
 */

@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun MyApp() {
    val navController = rememberNavController()

    val actions = remember(navController) { Actions(navController) }
    NavHost(navController = navController, startDestination = Destinations.HomeScreen) {

        composable(Destinations.HomeScreen) {
            MainScreen()
        }

    }

}