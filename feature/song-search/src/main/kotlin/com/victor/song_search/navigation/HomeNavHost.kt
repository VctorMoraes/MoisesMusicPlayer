package com.victor.song_search.navigation

import androidx.media3.session.MediaController
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.victor.song_search.presentation.SearchScreen

fun NavGraphBuilder.homeNavGraph(
    navController: NavController
) {
    composable(route = HomeNavHost.Search.name) {
        SearchScreen()
    }
    composable(
        route = "${HomeNavHost.SongDetails.name}/{songName}",
        arguments = listOf(
            navArgument(name = "songName") { NavType.StringType },
        )
    ) {
//        LoanApprovedScreen(it.arguments?.getString("maxAmount")?.toFloat() ?: 0F)
    }
}

enum class HomeNavHost {
    Search,
    SongDetails
}