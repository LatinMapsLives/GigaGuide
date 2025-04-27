package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import android.content.Context
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView


@OptIn(UnstableApi::class)
@Composable
fun AudioTestScreen(context: Context) {
    Log.d("APsP", "recomposed")

    val thePlayer = remember {
        ExoPlayer.Builder(context)
            .setLoadControl(
                DefaultLoadControl.Builder().apply { setBufferDurationsMs(1000, 120000, 500, 1000) }
                    .build()
            ).build()
    }

    // Создание MediaItem из URL
    val mediaItem =
        MediaItem.fromUri("https://drive.google.com/uc?export=download&id=1LecUqI3_67PO6F2jppHWtfFbKlWHz4Ar".toUri())

    // Создание MediaSource
    val dataSourceFactory = DefaultDataSource.Factory(context)
    val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
        .createMediaSource(mediaItem)

    // Запуск воспроизведения
    thePlayer.setMediaSource(mediaSource)
    thePlayer.prepare()
    thePlayer.playWhenReady = true

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = thePlayer
            }
        },
        update = {
            Log.d("APsP", "recomposed player view")
        }
    )
}

