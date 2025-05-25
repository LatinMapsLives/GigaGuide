package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
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
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumBlue
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.White
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.CurrentThemeSettings
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.GeoLocationProvider
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.Pancake
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.ExploreTourScreenViewModel
import kotlin.math.max


@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun ExploreTourScreen(
    context: Context,
    exploreTourScreenViewModel: ExploreTourScreenViewModel = hiltViewModel<ExploreTourScreenViewModel>(),
    navController: NavController,
    tourId: Long,
    locationProvider: GeoLocationProvider
) {
    DisposableEffect(Unit) {
        onDispose {
            exploreTourScreenViewModel.stopLoop()
            exploreTourScreenViewModel.player.release()
        }
    }
    LaunchedEffect(Unit) {
        exploreTourScreenViewModel.tourId = tourId
        exploreTourScreenViewModel.player = ExoPlayer.Builder(context).build()
        exploreTourScreenViewModel.player.addListener(object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                exploreTourScreenViewModel.currentTrackDurationMs.longValue =
                    exploreTourScreenViewModel.player.duration
            }

            override fun onTimelineChanged(timeline: Timeline, reason: Int) {
                exploreTourScreenViewModel.currentTrackDurationMs.longValue =
                    exploreTourScreenViewModel.player.duration
            }
        })
        exploreTourScreenViewModel.player.pauseAtEndOfMediaItems = true
        exploreTourScreenViewModel.player.playWhenReady = false
        exploreTourScreenViewModel.player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                exploreTourScreenViewModel.playerIsLoading.value =
                    playbackState != Player.STATE_READY
            }
        })
        exploreTourScreenViewModel.loadTour()
        exploreTourScreenViewModel.launchLoop(10000) {
            locationProvider.getCurrentLocation({
                exploreTourScreenViewModel.saveCurrentLocation(MapPoint(it.first, it.second))
            }, {
                exploreTourScreenViewModel.stopLoop()
                Pancake.info("Ошибка получения геолокации")
            })
        }
    }
    var dark = CurrentThemeSettings.isAppInDarkTheme()
    var deselectMomentCallback: () -> Unit = {
        exploreTourScreenViewModel.momentIsSelected = false
        exploreTourScreenViewModel.player.playWhenReady = false
        if (exploreTourScreenViewModel.player.isPlaying) exploreTourScreenViewModel.player.pause()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(modifier = Modifier.fillMaxSize(), factory = { context ->
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
                        exploreTourScreenViewModel.center = getMapCenter(null) as GeoPoint
                        return true
                    }

                    override fun onZoom(event: ZoomEvent?): Boolean {
                        exploreTourScreenViewModel.zoom = zoomLevelDouble
                        return false
                    }
                })
            }
        }, update = { view ->

            exploreTourScreenViewModel.animateToCurrentLocationCallback = {
                view.controller.animateTo(
                    GeoPoint(
                        exploreTourScreenViewModel.userLocation.value.latitude,
                        exploreTourScreenViewModel.userLocation.value.longitude
                    ),
                    17.0,
                    400
                )
            }

            view.controller.setCenter(exploreTourScreenViewModel.center)
            view.controller.setZoom(exploreTourScreenViewModel.zoom)

            if (!exploreTourScreenViewModel.tourRoute.isEmpty()
                && !exploreTourScreenViewModel.loadingTour
            ) {
                view.overlays.clear()
                view.overlays.add(MapEventsOverlay(object : MapEventsReceiver {
                    override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                        if (exploreTourScreenViewModel.momentIsSelected) {
                            deselectMomentCallback.invoke()
                        } else if (exploreTourScreenViewModel.exploringSight.value) {
                            exploreTourScreenViewModel.exploringSight.value = false
                        } else if (exploreTourScreenViewModel.sightIsSelected) {
                            exploreTourScreenViewModel.sightIsSelected = false
                        }
                        exploreTourScreenViewModel.player.pause()
                        return false
                    }

                    override fun longPressHelper(p: GeoPoint?): Boolean {
                        return false
                    }
                }))

                if (locationProvider.hasLocationPermissions()) {
                    var userMarker = Marker(view)
                    userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                    userMarker.position = GeoPoint(
                        exploreTourScreenViewModel.userLocation.value.latitude,
                        exploreTourScreenViewModel.userLocation.value.longitude
                    )
                    userMarker.icon = ResourcesCompat.getDrawable(
                        view.resources,
                        R.drawable.user_location_marker,
                        null
                    )
                    view.overlays.add(userMarker)
                }

                var tourRouteLine = Polyline()
                tourRouteLine.width = 5f
                tourRouteLine.color =
                    MediumBlue.copy(if (exploreTourScreenViewModel.exploringSight.value) 0.4f else 1f)
                        .toArgb()
                for (point in exploreTourScreenViewModel.tourRoute) {
                    tourRouteLine.addPoint(GeoPoint(point.latitude, point.longitude))
                }
                view.overlays.add(tourRouteLine)
                for (i in 0..exploreTourScreenViewModel.sightsOnMapInfos.size - 1) {
                    var sightOnMapInfo = exploreTourScreenViewModel.sightsOnMapInfos[i]
                    view.overlays.add(
                        numberedMarker(
                            number = i + 1,
                            point = GeoPoint(sightOnMapInfo.latitude, sightOnMapInfo.longitude),
                            selected = (exploreTourScreenViewModel.sightIsSelected || exploreTourScreenViewModel.exploringSight.value) && exploreTourScreenViewModel.selectedSightIndex.intValue == i,
                            mapView = view,
                            onClick = {
                                exploreTourScreenViewModel.sightIsSelected = true
                                exploreTourScreenViewModel.selectedSightIndex.intValue = i
                                view.controller.animateTo(
                                    GeoPoint(
                                        sightOnMapInfo.latitude,
                                        sightOnMapInfo.longitude
                                    ), 18.0, 400
                                )
                            },
                            alpha = if (exploreTourScreenViewModel.exploringSight.value) 0.4f else 1f
                        )
                    )
                }
                if (exploreTourScreenViewModel.exploringSight.value) {
                    var sightRouteLine = Polyline()
                    sightRouteLine.width = 5f
                    sightRouteLine.color =
                        MediumBlue.toArgb()
                    for (point in exploreTourScreenViewModel.sightRoutes[exploreTourScreenViewModel.selectedSightIndex.intValue]) {
                        sightRouteLine.addPoint(GeoPoint(point.latitude, point.longitude))
                    }
                    view.overlays.add(sightRouteLine)
                    for (i in 0..exploreTourScreenViewModel.momentOnMaps[exploreTourScreenViewModel.selectedSightIndex.intValue].size - 1) {
                        var momentOnMapInfo =
                            exploreTourScreenViewModel.momentOnMaps[exploreTourScreenViewModel.selectedSightIndex.intValue][i]
                        view.overlays.add(
                            numberedMarker(
                                number = i + 1,
                                point = GeoPoint(
                                    momentOnMapInfo.latitude,
                                    momentOnMapInfo.longitude
                                ),
                                selected = exploreTourScreenViewModel.momentIsSelected && exploreTourScreenViewModel.selectedMomentIndex.intValue == i,
                                mapView = view,
                                onClick = {
                                    exploreTourScreenViewModel.momentIsSelected = true
                                    exploreTourScreenViewModel.selectedMomentIndex.intValue = i
                                    exploreTourScreenViewModel.needToAnimateTo =
                                        GeoPoint(
                                            momentOnMapInfo.latitude,
                                            momentOnMapInfo.longitude
                                        )

                                    exploreTourScreenViewModel.player.seekTo(
                                        exploreTourScreenViewModel.indexesMap[exploreTourScreenViewModel.selectedSightIndex.intValue][i],
                                        0
                                    )
                                    exploreTourScreenViewModel.player.playWhenReady = true
                                },
                                alpha = 1f
                            )
                        )
                    }
                    if (exploreTourScreenViewModel.needToAnimateTo != null) {
                        view.controller.animateTo(
                            exploreTourScreenViewModel.needToAnimateTo!!,
                            20.0,
                            400
                        )
                        exploreTourScreenViewModel.needToAnimateTo = null
                    }
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
                    if (!locationProvider.hasLocationPermissions()) {
                        Pancake.info(noPermissionsString)
                    } else {
                        exploreTourScreenViewModel.animateToCurrentLocationCallback.invoke()
                    }
                })

            }
            AnimatedVisibility(
                visible = exploreTourScreenViewModel.sightIsSelected,
                modifier = Modifier
                    .fillMaxWidth(),
                enter = slideInVertically(initialOffsetY = { h -> h }),
                exit = slideOutVertically(targetOffsetY = { h -> h })
            ) {
                SightBox(
                    modifier = Modifier.fillMaxWidth(),
                    onButtonClick = {
                        if (exploreTourScreenViewModel.momentOnMaps[exploreTourScreenViewModel.selectedSightIndex.intValue].isNotEmpty()) {
                            exploreTourScreenViewModel.selectedMomentIndex.intValue = 0
                            exploreTourScreenViewModel.momentIsSelected = true
                            var m =
                                exploreTourScreenViewModel.momentOnMaps[exploreTourScreenViewModel.selectedSightIndex.intValue][0]
                            exploreTourScreenViewModel.needToAnimateTo =
                                GeoPoint(m.latitude, m.longitude)
                            exploreTourScreenViewModel.player.seekTo(
                                exploreTourScreenViewModel.indexesMap[exploreTourScreenViewModel.selectedSightIndex.intValue][0],
                                0
                            )
                            exploreTourScreenViewModel.player.playWhenReady = false
                        }
                        exploreTourScreenViewModel.sightIsSelected = false
                        exploreTourScreenViewModel.exploringSight.value = true
                    },
                    buttonText = "Раскрыть место",
                    sightOnMapInfo = exploreTourScreenViewModel.sightsOnMapInfos[exploreTourScreenViewModel.selectedSightIndex.intValue],
                    closeCallBack = {
                        exploreTourScreenViewModel.sightIsSelected = false
                    },
                    spacerHeight = 0.dp
                )
            }
            AnimatedVisibility(
                visible = exploreTourScreenViewModel.momentIsSelected,
                modifier = Modifier
                    .fillMaxWidth(),
                enter = slideInVertically(initialOffsetY = { h -> h }),
                exit = slideOutVertically(targetOffsetY = { h -> h })
            ) {
                MomentBox(
                    isPlayerLoading = exploreTourScreenViewModel.playerIsLoading.value,
                    momentNumber = exploreTourScreenViewModel.selectedMomentIndex.intValue + 1,
                    momentDurationMs = max(
                        exploreTourScreenViewModel.currentTrackDurationMs.longValue,
                        1
                    ),
                    currentPositionMs = exploreTourScreenViewModel.currentTrackPositionMs.longValue,
                    momentName = exploreTourScreenViewModel.currentMoment()!!.name,
                    nextOnClick = {
                        exploreTourScreenViewModel.player.seekToNextMediaItem()
                        exploreTourScreenViewModel.player.playWhenReady = true
                        exploreTourScreenViewModel.selectedMomentIndex.intValue++
                        exploreTourScreenViewModel.needToAnimateTo = GeoPoint(
                            exploreTourScreenViewModel.currentMoment()!!.latitude,
                            exploreTourScreenViewModel.currentMoment()!!.longitude
                        )
                    },
                    previousOnClick = {
                        exploreTourScreenViewModel.player.seekToPreviousMediaItem()
                        exploreTourScreenViewModel.player.playWhenReady = true
                        exploreTourScreenViewModel.selectedMomentIndex.intValue--
                        exploreTourScreenViewModel.needToAnimateTo = GeoPoint(
                            exploreTourScreenViewModel.currentMoment()!!.latitude,
                            exploreTourScreenViewModel.currentMoment()!!.longitude
                        )
                    },
                    hasNext =
                        exploreTourScreenViewModel.selectedMomentIndex.intValue < exploreTourScreenViewModel.momentOnMaps[exploreTourScreenViewModel.selectedSightIndex.intValue].size - 1,
                    hasPrevious = exploreTourScreenViewModel.selectedMomentIndex.intValue > 0,
                    chevronDownOnClick = deselectMomentCallback,
                    seekCallbackSeconds = { sec ->
                        exploreTourScreenViewModel.player.seekTo((sec * 1000).toLong())
                    },
                    isPlaying = exploreTourScreenViewModel.player.isPlaying,
                    pausePlayButtonOnClick = {
                        if (exploreTourScreenViewModel.player.isPlaying) {
                            exploreTourScreenViewModel.player.pause()
                        } else {
                            exploreTourScreenViewModel.player.play()
                        }
                    },
                    momentImageLink = exploreTourScreenViewModel.currentMoment()!!.imageLink
                )
            }
        }


        AnimatedVisibility(
            visible = exploreTourScreenViewModel.loadingTour, modifier = Modifier
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