package com.victor.song_search

import androidx.paging.PagingData
import com.victor.model.SongModel
import kotlinx.coroutines.flow.Flow

interface SearchSongUseCase {
    operator fun invoke(term: String): Flow<PagingData<SongModel>>
}