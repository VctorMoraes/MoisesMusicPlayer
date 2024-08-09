package com.victor.search

import android.net.Uri
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.victor.exceptions.UnknownException
import com.victor.search.datasource.remote.SearchPagingSource
import com.victor.search.datasource.remote.api.SearchApi
import com.victor.search.datasource.remote.dto.SearchItemResponseDTO
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
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import java.io.File
import java.net.HttpURLConnection
import kotlin.test.Test

@RunWith(JUnit4::class)
class SearchPagingSourceTest {
    private val mockWebServer: MockWebServer by lazy { MockWebServer() }

    private val uriMock = mockk<Uri>()

    private lateinit var testPager: TestPager<Int, SearchItemResponseDTO>

    @Before
    fun setup() {
        mockWebServer.start()

        mockkStatic(Uri::class)
        every { Uri.parse(any()) } returns uriMock

        val json = Json {
            ignoreUnknownKeys = true
        }

        val searchApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(OkHttpClient())
            .build()
            .create(SearchApi::class.java)

        val pagingSource = SearchPagingSource(
            searchApi,
            "megadeth"
        )

        testPager = TestPager(
            config = PagingConfig(
                pageSize = SearchPagingSource.SEARCH_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSource = pagingSource
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `pagingSource load returns page when first load is successful`() = runTest {
        val jsonSuccessResponse =
            File("src/test/kotlin/com/victor/search/results/song_success_1.json").readText()
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(jsonSuccessResponse)

        mockWebServer.enqueue(response)

        val expectedSearchResponseDTO = SearchItemResponseDTO(
            wrapperType = "track",
            trackId = 725812418,
            artistName = "Megadeth",
            collectionId = 725812243,
            collectionName = "Countdown to Extinction (Bonus Track Version)",
            trackName = "Symphony of Destruction",
            artworkUrl100 = "https://song_artwork_url.jpg",
            songPreviewUrl = "https://song_preview_url.mp4"
        )

        val result = testPager.refresh() as PagingSource.LoadResult.Page

        Assert.assertEquals(5, result.data.size)
        Assert.assertEquals(expectedSearchResponseDTO, result.data[0])
    }

    @Test
    fun `pagingSource load more returns correct page when second load is successful`() = runTest {
        val jsonFirstPageSuccessResponse =
            File("src/test/kotlin/com/victor/search/results/song_success_1.json").readText()
        val firstPageResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(jsonFirstPageSuccessResponse)
        mockWebServer.enqueue(firstPageResponse)

        val expectedFirstPageSize = 5
        val firstPageLoadResult = testPager.refresh() as PagingSource.LoadResult.Page

        Assert.assertEquals(expectedFirstPageSize, firstPageLoadResult.data.size)

        val jsonSecondPageSuccessResponse =
            File("src/test/kotlin/com/victor/search/results/song_success_2.json").readText()
        val secondPageResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(jsonSecondPageSuccessResponse)
        mockWebServer.enqueue(secondPageResponse)

        val expectedSearchResponseDTO = SearchItemResponseDTO(
            wrapperType = "track",
            trackId = 725812422,
            artistName = "Megadeth",
            collectionId = 725812243,
            collectionName = "Countdown to Extinction (Bonus Track Version)",
            trackName = "Architecture of Aggression",
            artworkUrl100 = "https://song_artwork_url.jpg",
            songPreviewUrl = "https://song_preview_url.mp4"
        )

        val expectedSecondPageSize = 1
        val secondPageLoadResult = testPager.append() as PagingSource.LoadResult.Page

        Assert.assertEquals(expectedSecondPageSize, secondPageLoadResult.data.size)
        Assert.assertEquals(expectedSearchResponseDTO, secondPageLoadResult.data[0])
    }

    @Test
    fun `pagingSource load returns exception when first load fails`() = runTest {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED)

        mockWebServer.enqueue(response)

        val result = testPager.refresh() as PagingSource.LoadResult.Error
        Assert.assertEquals(UnknownException::class.java, result.throwable.javaClass)
        Assert.assertThrows(UnknownException::class.java) {
            throw result.throwable
        }
    }
}