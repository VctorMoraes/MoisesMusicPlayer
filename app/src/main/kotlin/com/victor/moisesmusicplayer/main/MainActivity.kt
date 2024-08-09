package com.victor.moisesmusicplayer.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.victor.moisesmusicplayer.core.navigation.MoisesMusicPlayerNavHost
import com.victor.moisesmusicplayer.core.ui.theme.MoisesMusicPlayerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            MoisesMusicPlayerTheme {
                Column {
                    val navController = rememberNavController()
                    MoisesMusicPlayerNavHost(navController)
                }
            }
        }
    }
}