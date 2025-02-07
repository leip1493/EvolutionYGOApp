package com.leip1493.evolutionygoapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leip1493.evolutionygoapp.home.ui.HomeScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            HomeScreen()
        }
    }
}