package com.victor.media_player_service.util

import androidx.media3.session.MediaController
import com.victor.media_player_service.mapper.toMediaItem
import com.victor.model.MediaModel

fun MediaController.playPlaylist(playlist: List<MediaModel?>, songId: String) {
    clearMediaItems()

    var shouldSeekToNext = true
    playlist.forEach { mediaModel ->
        mediaModel?.let {
            addMediaItem(it.toMediaItem())

            if (currentMediaItem?.mediaId == songId) {
                shouldSeekToNext = false
            }
            if (shouldSeekToNext) {
                seekToNextMediaItem()
            }
        }
    }

    playWhenReady = true
    prepare()
}
