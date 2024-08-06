package com.victor.song_player.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LongState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.victor.feature.song_player.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    currentProgress: LongState,
    currentDuration: LongState,
    onProgressValueChanged: (Float) -> Unit,
    onPreviousClicked: () -> Unit,
    onPlayPauseClicked: () -> Unit,
    onNextClicked: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProgressSlider(
            currentProgress = currentProgress,
            currentDuration = currentDuration
        ) {
            onProgressValueChanged(it)
        }
        Controls(
            modifier = Modifier.padding(top = 16.dp),
            onPreviousClicked = { onPreviousClicked() },
            onPlayPauseClicked = { onPlayPauseClicked() },
            onNextClicked = { onNextClicked() }
        )


    }
}

@Composable
fun ProgressSlider(
    modifier: Modifier = Modifier,
    currentProgress: LongState,
    currentDuration: LongState,
    onValueChangeFinished: (Float) -> Unit
) {
    var isSliding by remember { mutableStateOf(false) }
    var slidingPosition by remember { mutableFloatStateOf(0f) }
    val duration by remember { currentDuration }
    val progress by remember { currentProgress }

    Column(modifier = modifier) {
        Slider(
            modifier = Modifier.padding(horizontal = 14.dp),
            value = if (isSliding) {
                slidingPosition
            } else {
                progress.toFloat()
            },
            onValueChange = {
                slidingPosition = it
                isSliding = true
            },
            onValueChangeFinished = {
                onValueChangeFinished(slidingPosition)
                isSliding = false
            },
            valueRange = 0f..duration.toFloat(),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFFFFFFFF),
                activeTrackColor = Color(0xFFFFFFFF),
                inactiveTrackColor = Color(0x40FFFFFF),
            )
        )

        ProgressText(
            modifier = Modifier.padding(horizontal = 20.dp),
            isSliding = isSliding,
            slidingPosition = slidingPosition.toLong(),
            progress = progress,
            duration = duration
        )
    }
}

@Composable
fun ProgressText(
    modifier: Modifier = Modifier,
    isSliding: Boolean,
    slidingPosition: Long,
    progress: Long,
    duration: Long
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val currentProgress = if (isSliding) {
            slidingPosition
        } else {
            progress
        }

        val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())

        Text(text = dateFormat.format(Date(currentProgress)))
        Text(text = "-${dateFormat.format(Date(duration - currentProgress))}")
    }
}

@Composable
fun Controls(
    modifier: Modifier = Modifier,
    onPreviousClicked: () -> Unit,
    onPlayPauseClicked: () -> Unit,
    onNextClicked: () -> Unit,
) {
    Row(
        modifier = modifier
            .height(64.dp)
            .width(200.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(48.dp)
                .rotate(180f)
                .clickable {
                    onPreviousClicked()

                },
            painter = painterResource(id = R.drawable.seek),
            contentDescription = "goBack"
        )
        Image(
            modifier = Modifier
                .size(64.dp)
                .clickable {
                    onPlayPauseClicked()
                },
            painter = painterResource(id = R.drawable.play_pause),
            contentDescription = "goBack"
        )
        Image(
            modifier = Modifier
                .size(48.dp)
                .clickable {
                    onNextClicked()
                },
            painter = painterResource(id = R.drawable.seek),
            contentDescription = "goBack"
        )
    }
}