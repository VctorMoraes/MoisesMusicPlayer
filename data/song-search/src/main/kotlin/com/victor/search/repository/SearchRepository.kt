package com.victor.search.repository

import androidx.paging.PagingData
import com.victor.model.MediaModel
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun searchByTerm(term: String): Flow<PagingData<MediaModel>>

    fun searchAlbumById(id: String): Flow<List<MediaModel>>
}