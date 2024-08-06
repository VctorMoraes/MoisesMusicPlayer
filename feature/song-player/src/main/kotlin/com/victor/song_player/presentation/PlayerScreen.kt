package com.victor.song_player.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.victor.song_player.composables.PlayerControls
import com.victor.song_player.viewmodel.PlayerViewModel

@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel = hiltViewModel()
) {
    Scaffold { paddingValues ->
        val songPagingItems =
            viewModel.songState.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            songPagingItems.value?.let {
                AsyncImage(
                    modifier = Modifier
                        .padding(top = 120.dp)
                        .size(200.dp)
                        .clip(RoundedCornerShape(38.dp))
                        .align(Alignment.CenterHorizontally),
                    model = it.songImageUri,
                    contentDescription = null,
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = it.songName,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp, start = 20.dp, end = 20.dp),
                    text = it.artistName,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleSmall
                )
            }

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
        }
    }
}
