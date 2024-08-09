package com.victor.song_player.viewmodel

import android.app.Application
import android.content.ComponentName
import androidx.compose.runtime.LongState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.asLongState
import androidx.compose.runtime.mutableLongStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import com.victor.album_search.SearchAlbumByIdUseCase
import com.victor.media_player_service.MediaPlayerService
import com.victor.media_player_service.mapper.toMediaModel
import com.victor.media_player_service.util.playPlaylist
import com.victor.model.MediaModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    application: Application,
    private val searchAlbumByIdUseCase: SearchAlbumByIdUseCase
) : AndroidViewModel(application) {
    private val applicationContext = application.applicationContext
    private lateinit var mediaController: MediaController

    private val _currentMedia: MutableStateFlow<MediaModel?> =
        MutableStateFlow(value = null)
    val currentMedia: StateFlow<MediaModel?>
        get() = _currentMedia.asStateFlow()


    private val _albumState: MutableStateFlow<List<MediaModel?>> =
        MutableStateFlow(value = emptyList())
    val albumState: StateFlow<List<MediaModel?>>
        get() = _albumState.asStateFlow()


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
                setupAlbumListener()
                setupProgressListener()
            },
            MoreExecutors.directExecutor()
        )
    }

    private fun setupMetadataListeners() {
        _currentMedia.value = mediaController.currentMediaItem?.toMediaModel()

        mediaController.addListener(object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)

                _currentMedia.value = mediaItem?.toMediaModel()
            }
        })
    }

    private fun setupAlbumListener() {
        viewModelScope.launch {
            currentMedia.collectLatest {
                it?.albumId?.let { albumId ->
                    searchAlbumByIdUseCase(albumId).collectLatest { mediaModelList ->
                        _albumState.value = mediaModelList
                    }
                }
            }
        }
    }

    fun playAlbum(clickedSongId: String) {
        albumState.value.let {
            mediaController.playPlaylist(it, clickedSongId)
        }
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