package com.victor.model

import android.net.Uri

data class MediaModel(
    val mediaType: MediaType,
    val songId: String,
    val songName: String,
    val artistName: String,
    val albumId: String,
    val albumName: String,
    val songImageUri: Uri?,
    val songPreviewUri: Uri?
)

enum class MediaType {
    SONG,
    ALBUM,
    UNKNOWN
}

