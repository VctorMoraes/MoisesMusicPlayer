package com.victor.media_player_service.mapper

import android.net.Uri
import androidx.core.os.bundleOf
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.victor.model.MediaModel
import com.victor.model.MediaType

fun MediaModel.toMediaItem(): MediaItem {
    return MediaItem.Builder()
        .setMediaId(songId)
        .setUri(songPreviewUri)
        .setMediaMetadata(
            MediaMetadata.Builder()
                .setTitle(songName)
                .setArtist(artistName)
                .setAlbumTitle(albumName)
                .setArtworkUri(songImageUri)
                .setExtras(bundleOf(ALBUM_ID_METADATA_EXTRA to albumId))
                .build()
        )
        .build()
}

fun MediaItem.toMediaModel(): MediaModel {
    with(mediaMetadata) {
        val albumId = extras?.getString(ALBUM_ID_METADATA_EXTRA) ?: ""

        return MediaModel(
            mediaType = MediaType.UNKNOWN,
            songId = mediaId,
            songName = title.toString(),
            artistName = artist.toString(),
            albumId = albumId,
            albumName = albumTitle.toString(),
            songImageUri = artworkUri ?: Uri.EMPTY,
            songPreviewUri = Uri.EMPTY
        )
    }
}

private const val ALBUM_ID_METADATA_EXTRA = "ALBUM_ID_METADATA_EXTRA"