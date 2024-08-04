package com.victor.model

import android.net.Uri

data class SongModel(
    val songId: Long,
    val songName: String,
    val artistName: String,
    val albumName: String,
    val songImageUrl: String,
    val songPreviewUri: Uri
)