package com.victor.moisesmusicplayer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoisesMusicPlayerApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}