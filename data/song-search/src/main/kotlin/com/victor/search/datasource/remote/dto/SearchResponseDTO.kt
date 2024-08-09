package com.victor.search.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDTO(
    @SerialName("resultCount") val resultCount: Int = 0,
    @SerialName("results") val results: List<SearchItemResponseDTO> = emptyList(),
)

@Serializable
data class SearchItemResponseDTO(
    @SerialName("wrapperType") val wrapperType: String = "",
    @SerialName("trackId") val trackId: Long = 0L,
    @SerialName("artistName") val artistName: String = "",
    @SerialName("collectionId") val collectionId: Long = 0L,
    @SerialName("collectionName") val collectionName: String = "",
    @SerialName("trackName") val trackName: String = "",
    @SerialName("artworkUrl100") val artworkUrl100: String? = null,
    @SerialName("previewUrl") val songPreviewUrl: String? = null
)