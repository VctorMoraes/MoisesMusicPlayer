package com.victor.search

import android.net.Uri
import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.victor.model.MediaModel
import com.victor.model.MediaType
import com.victor.search.datasource.remote.api.SearchApi
import com.victor.search.repository.SearchRepository
import com.victor.search.repository.SearchRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import java.io.File
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class SearchRepositoryTest {
    private val mockWebServer: MockWebServer by lazy { MockWebServer() }

    val uriMock = mockk<Uri>()

    private lateinit var repository: SearchRepository


    @Before
    fun setup() {
        mockWebServer.start()

        mockkStatic(Uri::class)
        every { Uri.parse(any()) } returns uriMock

        val json = Json {
            ignoreUnknownKeys = true
        }

        val apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(OkHttpClient())
            .build()
            .create(SearchApi::class.java)

        repository = SearchRepositoryImpl(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `search album by id should return correct results`() = runTest {
        turbineScope {
            val jsonSuccessResponse =
                File("src/test/kotlin/com/victor/search/results/album_success.json").readText()
            val response = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(jsonSuccessResponse)

            mockWebServer.enqueue(response)

            val expectedMediaModel = MediaModel(
                mediaType = MediaType.SONG,
                songId = "725812407",
                songName = "Skin O' My Teeth",
                artistName = "Megadeth",
                albumId = "725812243",
                albumName = "Countdown to Extinction (Bonus Track Version)",
                songImageUri = uriMock,
                songPreviewUri = uriMock
            )

            repository.searchAlbumById("megadeth").test {
                val mediaModelList = awaitItem()
                Assert.assertEquals(6, mediaModelList.size)
                Assert.assertEquals(MediaType.ALBUM, mediaModelList[0].mediaType)
                Assert.assertEquals(expectedMediaModel, mediaModelList[1])

                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}