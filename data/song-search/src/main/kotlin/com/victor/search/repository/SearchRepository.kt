package com.victor.search.repository

import androidx.paging.PagingData
import com.victor.model.SongModel
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun searchByTerm(term: String): Flow<PagingData<SongModel>>
}