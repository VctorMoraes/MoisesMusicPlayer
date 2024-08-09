package com.victor.search.mapper

import android.net.Uri
import androidx.core.net.toUri
import com.victor.model.MediaModel
import com.victor.model.MediaType
import com.victor.search.datasource.remote.dto.SearchItemResponseDTO

fun SearchItemResponseDTO.toModel() =
    MediaModel(
        mediaType = this.wrapperType.toMediaType(),
        songId = this.trackId.toString(),
        songName = this.trackName,
        artistName = this.artistName,
        albumId = this.collectionId.toString(),
        albumName = this.collectionName,
        songImageUri = this.artworkUrl100?.toUri() ?: Uri.EMPTY,
        songPreviewUri = this.songPreviewUrl?.toUri() ?: Uri.EMPTY
    )

private fun String.toMediaType(): MediaType {
    return when (this) {
        "collection" -> MediaType.ALBUM
        "track" -> MediaType.SONG
        else -> MediaType.UNKNOWN
    }

}