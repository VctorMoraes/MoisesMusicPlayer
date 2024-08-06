package com.victor.song_search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.victor.song_player.presentation.PlayerScreen
import com.victor.song_search.presentation.SearchScreen

fun NavController.navigateToPlayerScreen(navOptions: NavOptions? = null) =
    navigate(SongSearchNavHost.Player.name, navOptions)

fun NavGraphBuilder.songSearchNavGraph(
    navController: NavController
) {
    composable(route = SongSearchNavHost.Search.name) {
        SearchScreen {
            navController.navigateToPlayerScreen()
        }
    }
    composable(route = SongSearchNavHost.Player.name) {
        PlayerScreen()
    }
}

enum class SongSearchNavHost {
    Search,
    Player
}