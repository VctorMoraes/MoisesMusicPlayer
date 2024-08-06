package com.victor.moisesmusicplayer.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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