package com.victor.song_player.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.victor.composables.Song
import com.victor.feature.song_player.R
import com.victor.model.MediaModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreOptionsBottomSheet(
    modifier: Modifier = Modifier,
    songId: String,
    songName: String,
    songArtist: String,
    albumName: String,
    albumSongs: List<MediaModel?>,
    showBottomSheet: Boolean,
    onDismissRequest: () -> Unit,
    onSongClick: (String) -> Unit
) {
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var isAlbumsShown by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = Color(0xFF262626),
            modifier = modifier,
            onDismissRequest = {
                if (isAlbumsShown) {
                    isAlbumsShown = false
                }
                onDismissRequest()
            },
            sheetState = sheetState,
            windowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Top)
        ) {
            AnimatedVisibility(
                visible = !isAlbumsShown
            ) {
                MoreOptionsBottomSheetContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = bottomPadding),
                    songName = songName,
                    songArtist = songArtist,
                    onOpenAlbumClick = {
                        scope.launch { sheetState.expand() }
                            .invokeOnCompletion { isAlbumsShown = true }
                    }
                )
            }

            AnimatedVisibility(
                visible = isAlbumsShown
            ) {
                AlbumSongList(
                    modifier = Modifier.padding(bottom = bottomPadding),
                    songId = songId,
                    albumName = albumName,
                    albumSongs = albumSongs,
                    onSongClick = onSongClick
                )
            }
        }
    }
}

@Composable
private fun MoreOptionsBottomSheetContent(
    modifier: Modifier = Modifier,
    songName: String,
    songArtist: String,
    onOpenAlbumClick: () -> Unit
) {
    Column(modifier = modifier) {
        SongInfo(songName, songArtist)
        OpenAlbumButton(onOpenAlbumClick)
    }
}

@Composable
private fun ColumnScope.SongInfo(songName: String, songArtist: String) {
    Text(
        modifier = Modifier
            .align(Alignment.CenterHorizontally),
        text = songName,
        maxLines = 1,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.SemiBold
        )
    )
    Text(
        modifier = Modifier
            .padding(top = 4.dp)
            .align(Alignment.CenterHorizontally),
        text = songArtist,
        maxLines = 1,
        style = MaterialTheme.typography.labelLarge.copy(
            fontWeight = FontWeight.Medium
        )
    )
}

@Composable
private fun OpenAlbumButton(onOpenAlbumClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth()
            .height(60.dp)
            .clickable { onOpenAlbumClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 32.dp)
                .size(24.dp),
            painter = painterResource(id = R.drawable.ic_playlist),
            contentDescription = "Albums",
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(id = com.victor.common.strings.R.string.open_album),
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Composable
private fun AlbumSongList(
    modifier: Modifier = Modifier,
    songId: String,
    albumName: String,
    albumSongs: List<MediaModel?>,
    onSongClick: (String) -> Unit
) {
    Column(modifier = modifier.fillMaxHeight()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = albumName,
            textAlign = TextAlign.Center,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        LazyColumn(
            modifier = Modifier.padding(
                top = 16.dp
            )
        ) {
            items(albumSongs.size) {
                albumSongs[it]?.let { mediaModel ->
                    val isPlaying =
                        mutableStateOf(songId == mediaModel.songId)
                    val songNumber = it + 1
                    Song(
                        songName =
                        stringResource(
                            id = com.victor.common.strings.R.string.numbered_album,
                            songNumber,
                            mediaModel.songName
                        ),
                        artistName = mediaModel.artistName,
                        imageUri = mediaModel.songImageUri,
                        isPlaying = isPlaying
                    ) {
                        onSongClick(mediaModel.songId)
                        isPlaying.value = true
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MoreOptionsBottomSheetPreview() {
    MoreOptionsBottomSheetContent(
        songName = "Preview song name",
        songArtist = "Preview song Artist",
        onOpenAlbumClick = {}
    )
}
