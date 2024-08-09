package com.victor.media_player_service

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import androidx.annotation.OptIn
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.extractor.DefaultExtractorsFactory
import androidx.media3.extractor.mp4.Mp4Extractor
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

class MediaPlayerService : MediaSessionService() {
    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()

        val player = setupPlayer()

        val intent = packageManager.getLaunchIntentForPackage(packageName)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT)

        mediaSession = MediaSession.Builder(this, player)
            .setSessionActivity(pendingIntent)
            .build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        clearMediaSession()
        stopSelf()
    }

    override fun onDestroy() {
        clearMediaSession()
        super.onDestroy()
    }

    @OptIn(UnstableApi::class)
    private fun setupPlayer(): ExoPlayer {
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        val defaultExtractorsFactory: DefaultExtractorsFactory =
            DefaultExtractorsFactory().setMp4ExtractorFlags(Mp4Extractor.FLAG_WORKAROUND_IGNORE_EDIT_LISTS)

        val mediaSourceFactory: MediaSource.Factory =
            ProgressiveMediaSource.Factory(dataSourceFactory, defaultExtractorsFactory)

        val player = ExoPlayer.Builder(this)
            .setMediaSourceFactory(mediaSourceFactory)
            .setAudioAttributes(AudioAttributes.DEFAULT, true)
            .setHandleAudioBecomingNoisy(true)
            .setWakeMode(C.WAKE_MODE_LOCAL)
            .build()

        return player
    }

    private fun clearMediaSession() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
    }
}