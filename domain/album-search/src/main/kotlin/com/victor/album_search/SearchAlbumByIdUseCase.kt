package com.victor.album_search

import com.victor.model.MediaModel
import kotlinx.coroutines.flow.Flow

interface SearchAlbumByIdUseCase {
    operator fun invoke(id: String): Flow<List<MediaModel>>
}