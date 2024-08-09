package com.victor.moisesmusicplayer.core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.victor.song_search.navigation.SongSearchNavHost
import com.victor.song_search.navigation.songSearchNavGraph

@Composable
fun MoisesMusicPlayerNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SongSearchNavHost.Search.name,
        modifier = Modifier
            .fillMaxSize()
    ) {
        songSearchNavGraph(navController)
    }
}