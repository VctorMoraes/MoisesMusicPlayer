package com.victor.song_search.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
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
        SearchScreen(onSongClick = {
            navController.navigateToPlayerScreen()
        })
    }
    composable(
        route = SongSearchNavHost.Player.name,
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        exitTransition = {
            slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        }
    ) {
        PlayerScreen() {
            navController.popBackStack()
        }
    }
}

enum class SongSearchNavHost {
    Search,
    Player
}