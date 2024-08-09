package com.victor.search.datasource.remote.api

import com.victor.search.datasource.remote.dto.SearchResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("/search")
    suspend fun searchByTerm(
        @Query("term") term: String,
        @Query("media") media: String = "music",
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
    ): Response<SearchResponseDTO>

    @GET("/lookup")
    suspend fun searchAlbumById(
        @Query("id") id: String,
        @Query("entity") media: String = "song",
    ): Response<SearchResponseDTO>

}