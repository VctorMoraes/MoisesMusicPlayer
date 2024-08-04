package com.victor.search.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.victor.model.SongModel
import com.victor.search.datasource.remote.SearchPagingSource
import com.victor.search.datasource.remote.SearchPagingSource.Companion.SEARCH_PAGE_SIZE
import com.victor.search.datasource.remote.api.SearchApi
import com.victor.search.mapper.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi
) : SearchRepository {

    override fun searchByTerm(term: String): Flow<PagingData<SongModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = SEARCH_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchPagingSource(searchApi, term) },
        ).flow.map { pagingData ->
            pagingData.map {
                it.toModel()
            }
        }
    }
}