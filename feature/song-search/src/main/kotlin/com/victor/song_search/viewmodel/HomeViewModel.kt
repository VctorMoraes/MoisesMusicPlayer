package com.victor.song_search.viewmodel

import android.app.Application
import android.content.ComponentName
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.common.util.concurrent.MoreExecutors
import com.victor.media_player_service.MediaPlayerService
import com.victor.model.SongModel
import com.victor.song_search.SearchSongUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    val searchSongUseCase: SearchSongUseCase
) : AndroidViewModel(application) {
    private val applicationContext = application.applicationContext
    private lateinit var mediaController: MediaController

    private val _songState: MutableStateFlow<PagingData<SongModel>> =
        MutableStateFlow(value = PagingData.empty())
    val songState: StateFlow<PagingData<SongModel>>
        get() = _songState.asStateFlow()

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
            },
            MoreExecutors.directExecutor()
        )
    }

    init {
        setupMediaController()
        searchSong("megadeth")
    }

    private fun searchSong(term: String) {
        viewModelScope.launch {
            searchSongUseCase(term)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect { songPagingData ->
                    _songState.update { songPagingData }
                }
        }
    }

    fun play(songPreviewUri: Uri) {
        val mediaItem = MediaItem.fromUri(songPreviewUri)
        mediaController.addMediaItem(mediaItem)
        mediaController.prepare()
        mediaController.play()
    }

}

