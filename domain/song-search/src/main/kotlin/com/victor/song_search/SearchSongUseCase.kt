package com.victor.song_search

import androidx.paging.PagingData
import com.victor.model.MediaModel
import kotlinx.coroutines.flow.Flow

interface SearchSongUseCase {
    operator fun invoke(term: String): Flow<PagingData<MediaModel>>
}