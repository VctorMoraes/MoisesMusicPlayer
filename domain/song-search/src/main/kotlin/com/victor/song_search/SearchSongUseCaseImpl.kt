package com.victor.song_search

import com.victor.search.repository.SearchRepository
import javax.inject.Inject

class SearchSongUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
) : SearchSongUseCase {

    override fun invoke(term: String) =
        searchRepository.searchByTerm(term)
}