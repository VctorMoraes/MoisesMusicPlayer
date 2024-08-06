package com.victor.search.mapper

import androidx.core.net.toUri
import com.victor.search.datasource.remote.dto.SearchItemResponseDTO
import com.victor.model.SongModel

fun SearchItemResponseDTO.toModel() =
    SongModel(
        songId = this.trackId,
        songName = this.trackName,
        artistName = this.artistName,
        albumName = this.collectionName,
        songImageUri = this.artworkUrl100.toUri(),
        songPreviewUri = this.songPreviewUrl.toUri()
    )