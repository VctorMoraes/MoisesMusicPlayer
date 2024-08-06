package com.victor.song_player.viewmodel

import android.app.Application
import android.content.ComponentName
import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.runtime.LongState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.asLongState
import androidx.compose.runtime.mutableLongStateOf
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import com.victor.media_player_service.MediaPlayerService
import com.victor.model.SongModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    application: Application,
) : AndroidViewModel(application) {
    private val applicationContext = application.applicationContext
    private lateinit var mediaController: MediaController

    private val _songState: MutableStateFlow<SongModel?> =
        MutableStateFlow(value = null)

    val songState: StateFlow<SongModel?>
        get() = _songState.asStateFlow()

    private val _progress: MutableState<Long> = mutableLongStateOf(0L)
    val progress: LongState
        get() = _progress.asLongState()


    private val _duration: MutableState<Long> = mutableLongStateOf(0L)
    val duration: LongState
        get() = _duration.asLongState()

    init {
        setupMediaController()
    }

    private fun setupMediaController() {
        val sessionToken = SessionToken(
            applicationContext,
            ComponentName(applicationContext, MediaPlayerService::class.java)
        )

        val controllerFuture =
            MediaController.Builder(applicationContext, sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                mediaController = controllerFuture.get()
                setupMetadataListeners()
                setupProgressListener()
            },
            MoreExecutors.directExecutor()
        )
    }


    private fun setupMetadataListeners() {
        with(mediaController.mediaMetadata) {
            _songState.value = SongModel(
                songId = 0L,
                songName = title.toString(),
                artistName = artist.toString(),
                albumName = albumTitle.toString(),
                songImageUri = artworkUri!!,
                songPreviewUri = "".toUri()
            )
        }

        mediaController.addListener(object : Player.Listener {
            @OptIn(UnstableApi::class)
            override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                super.onMediaMetadataChanged(mediaMetadata)

                _songState.value = SongModel(
                    songId = 0L,
                    songName = mediaMetadata.title.toString(),
                    artistName = mediaMetadata.artist.toString(),
                    albumName = mediaMetadata.albumTitle.toString(),
                    songImageUri = mediaMetadata.artworkUri!!,
                    songPreviewUri = "".toUri()
                )
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
            }
        })
    }

    fun next() {
        mediaController.seekToNext()
    }

    fun previous() {
        mediaController.seekToPrevious()
    }

    fun playPause() {
        mediaController.takeIf { it.isPlaying }?.pause() ?: mediaController.play()
    }

    fun seekTo(position: Float) {
        mediaController.seekTo(position.toLong())
    }

    private fun setupProgressListener() {
        viewModelScope.launch {
            while (true) {
                mediaController.takeIf { it.isPlaying }?.apply {
                    _duration.value = duration
                    _progress.value = currentPosition
                }
                delay(250)
            }
        }
    }
}