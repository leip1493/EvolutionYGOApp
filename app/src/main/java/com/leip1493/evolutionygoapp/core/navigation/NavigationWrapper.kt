package com.leip1493.evolutionygoapp.core.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.leip1493.evolutionygoapp.playerdetail.ui.PlayerDetailScreen
import com.leip1493.evolutionygoapp.topplayers.ui.TopPlayersScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = TopPlayers
    ) {
        composable<TopPlayers> {
            TopPlayersScreen(
                navigateToPlayerDetail = { id, season, banlist ->
                    navController.navigate(PlayerDetail(id, season, banlist))
                }
            )
        }

        composable<PlayerDetail> {
            val playerDetail: PlayerDetail = it.toRoute()
            PlayerDetailScreen(playerDetail)
        }
    }
}