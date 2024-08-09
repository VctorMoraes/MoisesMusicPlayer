package com.victor.song_search.viewmodel

import android.app.Application
import android.content.ComponentName
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.common.util.concurrent.MoreExecutors
import com.victor.media_player_service.MediaPlayerService
import com.victor.media_player_service.util.playPlaylist
import com.victor.model.MediaModel
import com.victor.song_search.SearchSongUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SongSearchViewModel @Inject constructor(
    application: Application,
    val searchSongUseCase: SearchSongUseCase
) : AndroidViewModel(application) {
    private val applicationContext = application.applicationContext
    private lateinit var mediaController: MediaController

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery: StateFlow<String>
        get() = _searchQuery.asStateFlow()


    private val _currentSongId: MutableStateFlow<String> = MutableStateFlow("")
    val currentSongId: StateFlow<String>
        get() = _currentSongId.asStateFlow()


    val songListState: StateFlow<PagingData<MediaModel>> = searchQuery
        .flatMapLatest { query ->
            if (query.isNotBlank()) {
                searchSongUseCase(query)
                    .distinctUntilChanged()
            } else {
                flow { emit(PagingData.empty()) }
            }
        }.cachedIn(
            viewModelScope
        ).stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )

    init {
        setupMediaController()
    }

    fun onSearchTrailingIconClick() {
        _searchQuery.value = ""
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun playPlaylist(playlist: List<MediaModel?>, songId: String) {
        if (songId == currentSongId.value) {
            return
        }

        mediaController.playPlaylist(playlist, songId)
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
                setupCurrentSong()
            },
            MoreExecutors.directExecutor()
        )
    }

    private fun setupCurrentSong() {
        _currentSongId.value = mediaController.currentMediaItem?.mediaId ?: ""

        mediaController.addListener(object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)

                _currentSongId.value = mediaItem?.mediaId ?: ""
            }
        })

    }
}

