package com.victor.song_search.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.victor.model.SongModel
import com.victor.song_search.viewmodel.HomeViewModel

@Composable
fun SearchScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val songPagingItems: LazyPagingItems<SongModel> = viewModel.songState.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .padding(24.dp)
            .height(50.dp)
    ) {
        items(songPagingItems.itemCount) { index ->
            songPagingItems[index]?.let { song ->
                Song(
                    songName = song.songName + index,
                    artistName = song.artistName,
                    imageUrl = song.songImageUrl
                ) {
                    viewModel.play(song.songPreviewUri)
                }
            }
        }

        songPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    Log.d("HomeActivity", "loadState.refresh loading song")
                }

                loadState.refresh is LoadState.Error -> {
                    val error = loadState.refresh as LoadState.Error
                    Log.d("HomeActivity", "loadState.refresh error $error")
                }

                loadState.append is LoadState.Loading -> {
                    Log.d("HomeActivity", "loadState.append loading song")
                }

                loadState.append is LoadState.Error -> {
                    val error = loadState.append as LoadState.Error
                    Log.d("HomeActivity", "loadState.append error $error")
                }
            }
        }
    }

}

@Composable
fun Song(
    modifier: Modifier = Modifier,
    songName: String,
    artistName: String,
    imageUrl: String,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(8.dp)),
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
        ) {
            Text(
                text = songName,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = artistName,
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall
            )
        }

    }
}
