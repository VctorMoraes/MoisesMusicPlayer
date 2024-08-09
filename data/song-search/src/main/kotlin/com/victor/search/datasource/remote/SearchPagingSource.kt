package com.victor.search.datasource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.victor.exceptions.NoConnectionException
import com.victor.exceptions.UnknownException
import com.victor.network.fold
import com.victor.search.datasource.remote.api.SearchApi
import com.victor.search.datasource.remote.dto.SearchItemResponseDTO
import retrofit2.HttpException

class SearchPagingSource(
    private val searchApi: SearchApi,
    private val term: String
) : PagingSource<Int, SearchItemResponseDTO>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItemResponseDTO> {
        val offset = params.key ?: SEARCH_STARTING_PAGE_INDEX

        return try {
            searchApi.searchByTerm(term = term, limit = params.loadSize, offset = offset)
                .fold({ searchResponse ->
                    searchResponse?.let {
                        val results = searchResponse.results
                        val nextKey = if (results.isEmpty()) {
                            null
                        } else {
                            offset + params.loadSize
                        }
                        LoadResult.Page(
                            data = results,
                            prevKey = if (offset == SEARCH_STARTING_PAGE_INDEX) null else offset - 1,
                            nextKey = nextKey
                        )
                    } ?: LoadResult.Error(UnknownException())
                }, {
                    LoadResult.Error(UnknownException())
                })
        } catch (exception: NoConnectionException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchItemResponseDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        const val SEARCH_PAGE_SIZE = 20
        private const val SEARCH_STARTING_PAGE_INDEX = 0
    }

}