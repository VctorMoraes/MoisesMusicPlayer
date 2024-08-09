package com.victor.composables

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.victor.core.common.composables.R

@Composable
fun Song(
    modifier: Modifier = Modifier,
    songName: String,
    artistName: String,
    imageUri: Uri?,
    isPlaying: State<Boolean> = mutableStateOf(false),
    onSongClick: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.song_playing))

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable { onSongClick() }
            .padding(vertical = 8.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(8.dp)),
            model = imageUri,
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
                style = MaterialTheme.typography.labelMedium
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (isPlaying.value) {
            LottieAnimation(
                modifier = Modifier.size(44.dp),
                composition = composition,
                iterations = LottieConstants.IterateForever
            )
        }
    }
}
