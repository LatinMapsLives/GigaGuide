package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Rect
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.TilesOverlay
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.MapPoint
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.LightGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumBlue
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.White
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.CurrentThemeSettings
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.GeoLocationProvider
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.Pancake
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.ExploreSightScreenViewModel
import kotlin.math.max


@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun ExploreSightScreen(
    context: Context,
    exploreSightScreenViewModel: ExploreSightScreenViewModel = hiltViewModel<ExploreSightScreenViewModel>(),
    navController: NavController,
    sightId: Long,
    locationProvider: GeoLocationProvider
) {
    DisposableEffect(Unit) {
        onDispose {
            exploreSightScreenViewModel.stopLoop()
            exploreSightScreenViewModel.player.release()
        }
    }
    LaunchedEffect(Unit) {
        exploreSightScreenViewModel.sightId = sightId
        exploreSightScreenViewModel.player = ExoPlayer.Builder(context).build()
        exploreSightScreenViewModel.player.addListener(object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                exploreSightScreenViewModel.currentTrackDurationMs.longValue =
                    exploreSightScreenViewModel.player.duration
            }

            override fun onTimelineChanged(timeline: Timeline, reason: Int) {
                exploreSightScreenViewModel.currentTrackDurationMs.longValue =
                    exploreSightScreenViewModel.player.duration
            }
        })
        exploreSightScreenViewModel.player.pauseAtEndOfMediaItems = true
        exploreSightScreenViewModel.player.playWhenReady = false
        exploreSightScreenViewModel.player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                exploreSightScreenViewModel.playerIsLoading.value =
                    playbackState != Player.STATE_READY
            }
        })
        exploreSightScreenViewModel.loadRoute()
        if (!exploreSightScreenViewModel.route.isEmpty() && !exploreSightScreenViewModel.momentOnMaps.isEmpty()) {
            exploreSightScreenViewModel.selectedMomentIndex.intValue = 0;
            exploreSightScreenViewModel.needToAnimateTo = GeoPoint(
                exploreSightScreenViewModel.currentMoment()!!.latitude,
                exploreSightScreenViewModel.currentMoment()!!.longitude
            )
            for (i in 0..exploreSightScreenViewModel.momentOnMaps.size - 1) {
                var momentOnMap =
                    exploreSightScreenViewModel.momentOnMaps[i]
                exploreSightScreenViewModel.player.addMediaItem(MediaItem.fromUri(momentOnMap.audioLink.toUri()))
            }
            exploreSightScreenViewModel.selected = true
        }
        exploreSightScreenViewModel.player.prepare()
        exploreSightScreenViewModel.player.seekTo(0, 0)
        exploreSightScreenViewModel.launchPositionUpdateLoop()
        exploreSightScreenViewModel.launchLoop(10000) {
            locationProvider.getCurrentLocation({
                exploreSightScreenViewModel.saveCurrentLocation(MapPoint(it.first, it.second))
            }, {
                exploreSightScreenViewModel.stopLoop()
                Pancake.info("Ошибка получения геолокации")
            })
        }
    }
    var dark = CurrentThemeSettings.isAppInDarkTheme()
    var deselectCallback: () -> Unit = {
        exploreSightScreenViewModel.selected = false
        exploreSightScreenViewModel.player.playWhenReady = false
        if (exploreSightScreenViewModel.player.isPlaying) exploreSightScreenViewModel.player.pause()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(modifier = Modifier.clip(RoundedCornerShape(1.dp)).fillMaxSize(), factory = { context ->
            MapView(context).apply {
                setBuiltInZoomControls(false)
                setMultiTouchControls(true)
                controller.setZoom(16.0)
                setTileSource(TileSourceFactory.MAPNIK)
                Configuration.getInstance().userAgentValue = "GigaGuide"
                if (dark) {
                    this.overlayManager.tilesOverlay.setColorFilter(TilesOverlay.INVERT_COLORS)
                }
                addMapListener(object : MapListener {
                    override fun onScroll(event: ScrollEvent?): Boolean {
                        exploreSightScreenViewModel.center = getMapCenter(null) as GeoPoint
                        return true
                    }

                    override fun onZoom(event: ZoomEvent?): Boolean {
                        exploreSightScreenViewModel.zoom = zoomLevelDouble
                        return false
                    }
                })
            }
        }, update = { view ->

            exploreSightScreenViewModel.animateToCurrentLocationCallback = {
                view.controller.animateTo(
                    GeoPoint(
                        exploreSightScreenViewModel.userLocation.value.latitude,
                        exploreSightScreenViewModel.userLocation.value.longitude
                    ),
                    17.0,
                    400
                )
            }

            view.controller.setCenter(exploreSightScreenViewModel.center)
            view.controller.setZoom(exploreSightScreenViewModel.zoom)

            if (!exploreSightScreenViewModel.route.isEmpty() && !exploreSightScreenViewModel.momentOnMaps.isEmpty() && !exploreSightScreenViewModel.loadingRoute) {


                view.overlays.clear()

                view.overlays.add(MapEventsOverlay(object : MapEventsReceiver {
                    override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                        deselectCallback.invoke()
                        return false
                    }

                    override fun longPressHelper(p: GeoPoint?): Boolean {
                        return false
                    }
                }))

                if(locationProvider.hasLocationPermissions()){
                    var userMarker = Marker(view)
                    userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                    userMarker.position = GeoPoint(
                        exploreSightScreenViewModel.userLocation.value.latitude,
                        exploreSightScreenViewModel.userLocation.value.longitude
                    )
                    userMarker.icon = ResourcesCompat.getDrawable(
                        view.resources,
                        R.drawable.user_location_marker,
                        null
                    )
                    view.overlays.add(userMarker)
                }

                var line = Polyline()
                line.width = 5f
                line.color = MediumBlue.toArgb()
                for (point in exploreSightScreenViewModel.route) {
                    line.addPoint(GeoPoint(point.latitude, point.longitude))
                }
                view.overlays.add(line)

                for (i in 0..exploreSightScreenViewModel.momentOnMaps.size - 1) {
                    var m = exploreSightScreenViewModel.momentOnMaps[i]
                    view.overlays.add(
                        numberedMarker(
                            number = i + 1,
                            point = GeoPoint(m.latitude, m.longitude),
                            selected = exploreSightScreenViewModel.selected && exploreSightScreenViewModel.selectedMomentIndex.intValue == i,
                            mapView = view,
                            onClick = {
                                exploreSightScreenViewModel.selected = true
                                exploreSightScreenViewModel.selectedMomentIndex.intValue = i
                                exploreSightScreenViewModel.needToAnimateTo =
                                    GeoPoint(m.latitude, m.longitude)
                                exploreSightScreenViewModel.player.seekTo(i, 0)
                                exploreSightScreenViewModel.player.playWhenReady = true
                            },
                            alpha = 1f
                        )
                    )
                }
                if (exploreSightScreenViewModel.needToAnimateTo != null) {
                    view.controller.animateTo(
                        exploreSightScreenViewModel.needToAnimateTo!!,
                        20.0,
                        400
                    )
                    exploreSightScreenViewModel.needToAnimateTo = null
                }
            }
        })
        Button(
            onClick = { navController.popBackStack() },
            contentPadding = PaddingValues(6.dp),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(20.dp)
                .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
                .height(50.dp)
                .aspectRatio(1f),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.secondary
            ),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.chevron_left),
                contentDescription = "chevron_left",
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                var noPermissionsString = stringResource(R.string.warning_no_location_permissions)
                UserLocationButton(onButtonClick = {
                    if(!locationProvider.hasLocationPermissions()){
                        Pancake.info(noPermissionsString)
                    } else {
                        exploreSightScreenViewModel.animateToCurrentLocationCallback.invoke()
                    }
                })

            }

            AnimatedVisibility(visible = exploreSightScreenViewModel.selected,
                enter = slideInVertically(initialOffsetY = { h -> h }),
                exit = slideOutVertically(targetOffsetY = { h -> h }),
                modifier = Modifier.fillMaxWidth()) {
                MomentBox(
                    isPlayerLoading = exploreSightScreenViewModel.playerIsLoading.value,
                    momentNumber = exploreSightScreenViewModel.selectedMomentIndex.intValue + 1,
                    momentDurationMs = max(
                        exploreSightScreenViewModel.currentTrackDurationMs.longValue,
                        1
                    ),
                    currentPositionMs = exploreSightScreenViewModel.currentTrackPositionMs.longValue,
                    momentName = exploreSightScreenViewModel.currentMoment()!!.name,
                    nextOnClick = {
                        exploreSightScreenViewModel.player.seekToNextMediaItem()
                        exploreSightScreenViewModel.player.playWhenReady = true
                        exploreSightScreenViewModel.selectedMomentIndex.intValue++
                        exploreSightScreenViewModel.needToAnimateTo = GeoPoint(
                            exploreSightScreenViewModel.currentMoment()!!.latitude,
                            exploreSightScreenViewModel.currentMoment()!!.longitude
                        )
                    },
                    previousOnClick = {
                        exploreSightScreenViewModel.player.seekToPreviousMediaItem()
                        exploreSightScreenViewModel.player.playWhenReady = true
                        exploreSightScreenViewModel.selectedMomentIndex.intValue--
                        exploreSightScreenViewModel.needToAnimateTo = GeoPoint(
                            exploreSightScreenViewModel.currentMoment()!!.latitude,
                            exploreSightScreenViewModel.currentMoment()!!.longitude
                        )
                    },
                    hasNext =
                        exploreSightScreenViewModel.selectedMomentIndex.intValue < exploreSightScreenViewModel.momentOnMaps.size - 1,
                    hasPrevious = exploreSightScreenViewModel.selectedMomentIndex.intValue > 0,
                    chevronDownOnClick = deselectCallback,
                    seekCallbackSeconds = { sec ->
                        exploreSightScreenViewModel.player.seekTo((sec * 1000).toLong())
                    },
                    isPlaying = exploreSightScreenViewModel.player.isPlaying,
                    pausePlayButtonOnClick = {
                        if (exploreSightScreenViewModel.player.isPlaying) {
                            exploreSightScreenViewModel.player.pause()
                        } else {
                            exploreSightScreenViewModel.player.play()
                        }
                    },
                    momentImageLink = exploreSightScreenViewModel.currentMoment()!!.imageLink
                )
            }
        }
        AnimatedVisibility(
            visible = exploreSightScreenViewModel.loadingRoute, modifier = Modifier
                .align(
                    Alignment.TopCenter
                )
                .padding(10.dp),
            enter = slideInVertically(initialOffsetY = { h -> (-1.5f * h).toInt() }),
            exit = slideOutVertically(targetOffsetY = { h -> (-1.5f * h).toInt() })
        ) {
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = MediumGrey)
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = White)
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = stringResource(R.string.explore_sight_screen_loading_route),
                    color = White
                )
            }
        }
    }
}

fun numberedMarker(
    number: Int,
    point: GeoPoint,
    selected: Boolean,
    mapView: MapView,
    onClick: () -> Unit,
    alpha: Float
): Marker {
    var marker = Marker(mapView)
    marker.position = point
    marker.alpha = alpha
    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
    var drawableId = if (selected) {
        R.drawable.moment_marker_selected
    } else {
        R.drawable.moment_marker_unselected
    }
    marker.setOnMarkerClickListener { marker, mapView ->
        onClick.invoke()
        return@setOnMarkerClickListener true
    }

    val drawable = ResourcesCompat.getDrawable(
        mapView.resources, drawableId, null
    )
    val bitmap = drawable!!.toBitmap()

    val paint = Paint();
    paint.style = Style.FILL
    paint.color = Color.WHITE
    paint.textSize = 40f

    val canvas = Canvas(bitmap)
    val text = number.toString()
    var r = Rect();
    canvas.getClipBounds(r)
    val cHeight: Int = r.height()
    val cWidth: Int = r.width()
    paint.textAlign = Paint.Align.LEFT
    paint.getTextBounds(text, 0, text.length, r)
    val x: Float = cWidth / 2f - r.width() / 2f - r.left
    val y: Float = cHeight / 2f + r.height() / 2f - r.bottom
    canvas.drawText(text, x, y, paint)


    val iconWithText = bitmap.toDrawable(mapView.resources)

    marker.icon = iconWithText

    return marker;
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MomentBox(
    modifier: Modifier = Modifier,
    momentName: String = "Момент1",
    momentNumber: Int = 1,
    momentImageLink: String,
    momentDurationMs: Long = 90,
    currentPositionMs: Long = 45,
    isPlayerLoading: Boolean = false,
    hasNext: Boolean = false,
    hasPrevious: Boolean = false,
    nextOnClick: () -> Unit,
    previousOnClick: () -> Unit,
    chevronDownOnClick: () -> Unit,
    seekCallbackSeconds: (Long) -> Unit,
    isPlaying: Boolean,
    pausePlayButtonOnClick: () -> Unit
) {
    GigaGuideMobileTheme {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier
                    .clickable(onClick = chevronDownOnClick)
                    .background(color = MaterialTheme.colorScheme.tertiary)
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
                    .height(10.dp),
                imageVector = ImageVector.vectorResource(R.drawable.chevron_down_flat),
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = "chevron down"
            )
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .dropShadow(offsetX = 0.dp, offsetY = 0.dp, blur = 16.dp)
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .aspectRatio(1f),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    model = momentImageLink
                )
                Column(
                    modifier = Modifier
                        .weight(weight = 1f, fill = true)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "$momentNumber. $momentName",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Row(
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .clickable(enabled = hasPrevious, onClick = previousOnClick)
                                .size(40.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.skip_previous),
                            contentDescription = null,
                            tint = if (hasPrevious) MediumBlue else MediumGrey
                        )
                        if (isPlayerLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                                    .size(44.dp)
                            )
                        } else {
                            Icon(
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                                    .clickable(onClick = pausePlayButtonOnClick)
                                    .clip(CircleShape)
                                    .background(color = MediumBlue)
                                    .padding(10.dp),
                                imageVector = ImageVector.vectorResource(if (isPlaying) R.drawable.pause else R.drawable.play),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.background
                            )
                        }

                        Icon(
                            modifier = Modifier
                                .clickable(enabled = hasNext, onClick = nextOnClick)
                                .size(40.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.skip_next),
                            contentDescription = null,
                            tint = if (hasNext) MediumBlue else MediumGrey
                        )
                    }
                    var temporaryValueStorage = remember { 0f }
                    Slider(
                        modifier = Modifier.height(20.dp),
                        enabled = true,
                        value = 1f * currentPositionMs / momentDurationMs,
                        onValueChangeFinished = {
                            seekCallbackSeconds.invoke((temporaryValueStorage * momentDurationMs).toLong() / 1000)
                        },
                        onValueChange = {
                            temporaryValueStorage = it
                        },
                        thumb = {
                            Spacer(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(color = MediumBlue)
                                    .width(16.dp)
                                    .aspectRatio(1f)
                            )
                        },
                        track = { state ->
                            CustomSlider(state)
                        }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val currentPositionMinutes: Long = currentPositionMs / 60000
                        val currentPositionSeconds: Long = (currentPositionMs % 60000) / 1000
                        val currentPositionStringSeconds: String =
                            (if (currentPositionSeconds < 10) "0" else "") + currentPositionSeconds.toString()
                        val durationMinutes: Long = momentDurationMs / 60000
                        val durationSeconds: Long = (momentDurationMs % 60000) / 1000
                        val durationStringSeconds: String =
                            (if (durationSeconds < 10) "0" else "") + durationSeconds.toString()
                        Text(
                            text = "$currentPositionMinutes:$currentPositionStringSeconds",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "$durationMinutes:$durationStringSeconds",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSlider(sliderState: SliderState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(color = LightGrey),
        contentAlignment = Alignment.CenterStart
    ) {
        Spacer(
            modifier = Modifier
                .background(color = MediumBlue)
                .fillMaxWidth(sliderState.value)
                .height(5.dp)
        )
    }
}