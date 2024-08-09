package com.victor.song_player.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.victor.song_player.composables.MoreOptionsBottomSheet
import com.victor.song_player.composables.PlayerControls
import com.victor.song_player.viewmodel.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel = hiltViewModel(),
    onBackClicked: () -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onBackClicked() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                id = com.victor.common.strings.R.string.go_back_content_description
                            )
                        )
                    }
                },
                title = {},
                actions = {
                    IconButton(onClick = { showBottomSheet = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = stringResource(
                                id = com.victor.common.strings.R.string.more_options_content_description
                            )
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        val currentMedia =
            viewModel.currentMedia.collectAsStateWithLifecycle()
        val albumSongs = viewModel.albumState.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            currentMedia.value?.let { mediaModel ->
                AsyncImage(
                    modifier = Modifier
                        .padding(top = 120.dp)
                        .size(200.dp)
                        .clip(RoundedCornerShape(38.dp))
                        .align(Alignment.CenterHorizontally),
                    model = mediaModel.songImageUri,
                    contentDescription = null,
                )

                Spacer(modifier = Modifier.weight(1f))

                SongInfo(mediaModel.songName, mediaModel.artistName)

                PlayerControls(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 26.dp)
                        .align(Alignment.End),
                    currentProgress = viewModel.progress,
                    currentDuration = viewModel.duration,
                    onProgressValueChanged = { viewModel.seekTo(it) },
                    onPreviousClicked = { viewModel.previous() },
                    onPlayPauseClicked = { viewModel.playPause() },
                    onNextClicked = { viewModel.next() },
                )

                MoreOptionsBottomSheet(
                    showBottomSheet = showBottomSheet,
                    songId = mediaModel.songId,
                    songName = mediaModel.songName,
                    songArtist = mediaModel.artistName,
                    albumName = mediaModel.albumName,
                    albumSongs = albumSongs.value,
                    onSongClick = {
                        viewModel.playAlbum(it)
                    },
                    onDismissRequest = {
                        showBottomSheet = false
                    }
                )
            }
        }
    }
}

@Composable
fun SongInfo(songName: String, artistName: String) {
    Text(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = songName,
        maxLines = 1,
        style = MaterialTheme.typography.titleLarge
    )
    Text(
        modifier = Modifier.padding(top = 4.dp, start = 20.dp, end = 20.dp),
        text = artistName,
        maxLines = 1,
        style = MaterialTheme.typography.labelLarge
    )
}
