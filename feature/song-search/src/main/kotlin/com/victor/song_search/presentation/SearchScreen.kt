package com.victor.song_search.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.victor.composables.Song
import com.victor.exceptions.NoConnectionException
import com.victor.feature.song_search.R
import com.victor.model.MediaModel
import com.victor.song_search.viewmodel.SongSearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SongSearchViewModel = hiltViewModel(),
    onSongClick: () -> Unit
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                title = {
                    Text(
                        text = "Songs",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                scrollBehavior = scrollBehavior
            )

        }
    ) { paddingValues ->
        val songPagingItems: LazyPagingItems<MediaModel> =
            viewModel.songListState.collectAsLazyPagingItems()

        val searchQuery = viewModel.searchQuery.collectAsStateWithLifecycle()
        val currentSongId = viewModel.currentSongId.collectAsStateWithLifecycle()

        SearchBar(
            modifier = Modifier.padding(paddingValues),
            query = searchQuery.value,
            onQueryChange = { viewModel.onSearchQueryChange(it) },
            onSearch = { },
            placeholder = { Text(text = "Search") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null
                )
            },
            trailingIcon = {
                val isLoadingSongs = songPagingItems.loadState.refresh is LoadState.Loading
                if (isLoadingSongs) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(32.dp),
                        color = Color.White
                    )
                } else {
                    IconButton(onClick = { viewModel.onSearchTrailingIconClick() }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = null
                        )
                    }
                }
            },
            colors = SearchBarDefaults.colors(
                containerColor = Color.Transparent,
                dividerColor = MaterialTheme.colorScheme.onSurface,
            ),
            windowInsets = WindowInsets(0, 0, 0, 0),
            active = true,
            onActiveChange = { },
        ) {
            if (songPagingItems.loadState.refresh is LoadState.Error) {
                val error = songPagingItems.loadState.refresh as LoadState.Error
                if (error.error is NoConnectionException) {
                    NoInternetConnection()
                }
            }

            LazyColumn {
                items(songPagingItems.itemCount) { index ->
                    songPagingItems[index]?.let { mediaModel ->
                        val isPlaying =
                            mutableStateOf(currentSongId.value == mediaModel.songId)

                        Song(
                            songName = mediaModel.songName,
                            artistName = mediaModel.artistName,
                            isPlaying = isPlaying,
                            imageUri = mediaModel.songImageUri
                        ) {
                            val playlist = List(10) {
                                val songIndex = index + it

                                if (songIndex < songPagingItems.itemCount) {
                                    songPagingItems[index + it]
                                } else {
                                    null
                                }
                            }
                            viewModel.playPlaylist(playlist, mediaModel.songId)
                            onSongClick()
                        }
                    }
                }

                if (songPagingItems.loadState.append is LoadState.Loading) {
                    item {
                        LazyItemProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
private fun NoInternetConnection() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(128.dp),
            painter = painterResource(id = R.drawable.no_connection),
            contentDescription = "No connection"
        )
        Text(
            modifier = Modifier.padding(horizontal = 64.dp),
            text = "No internet connection",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
private fun LazyItemProgressIndicator() {
    Column(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(44.dp),
            color = Color.White
        )
    }
}
