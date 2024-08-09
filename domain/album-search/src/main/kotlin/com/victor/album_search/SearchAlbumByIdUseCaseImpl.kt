package com.victor.album_search

import com.victor.model.MediaModel
import com.victor.model.MediaType
import com.victor.search.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchAlbumByIdUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
) : SearchAlbumByIdUseCase {
    override fun invoke(id: String): Flow<List<MediaModel>> =
        searchRepository.searchAlbumById(id).map { mediaModelList ->
            mediaModelList.filter { it.mediaType == MediaType.SONG }
        }

}