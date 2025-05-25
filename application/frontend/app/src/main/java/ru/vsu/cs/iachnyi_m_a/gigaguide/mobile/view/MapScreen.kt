package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
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
import org.osmdroid.views.overlay.TilesOverlay
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.MapPoint
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightOnMapInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.SightPageScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.Black
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumBlue
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumGrey
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.White
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.CurrentThemeSettings
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.GeoLocationProvider
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.Pancake
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.MapScreenViewModel


@Composable
fun MapScreen(
    mapScreenViewModel: MapScreenViewModel,
    navController: NavController,
    locationProvider: GeoLocationProvider
) {
    var dark = CurrentThemeSettings.isAppInDarkTheme()
    if (mapScreenViewModel.needToLoad) {
        mapScreenViewModel.loadSightsOnMap()
    }
    DisposableEffect(Unit) {
        onDispose {
            mapScreenViewModel.stopLoop()
        }
    }
    LaunchedEffect(Unit) {
        mapScreenViewModel.launchLoop(10000) {
            locationProvider.getCurrentLocation({
                mapScreenViewModel.saveCurrentLocation(MapPoint(it.first, it.second))
            }, {
                mapScreenViewModel.stopLoop()
                Pancake.info("Ошибка получения геолокации")
            })
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { context ->
                MapView(context).apply {
                    setMultiTouchControls(true)
                    controller.setZoom(16.0)
                    setTileSource(TileSourceFactory.MAPNIK)
                    Configuration.getInstance().userAgentValue = "GigaGuide"
                    if (dark) {
                        this.overlayManager.tilesOverlay.setColorFilter(TilesOverlay.INVERT_COLORS)
                    }
                    addMapListener(
                        object : MapListener {
                            override fun onScroll(event: ScrollEvent?): Boolean {
                                mapScreenViewModel.center = getMapCenter(null) as GeoPoint
                                return true
                            }

                            override fun onZoom(event: ZoomEvent?): Boolean {
                                mapScreenViewModel.zoom = zoomLevelDouble
                                return false
                            }
                        }
                    )
                }
            },
            update = { view ->
                mapScreenViewModel.animateToCurrentLocationCallback = {
                    view.controller.animateTo(
                        GeoPoint(
                            mapScreenViewModel.userLocation.value.latitude,
                            mapScreenViewModel.userLocation.value.longitude
                        ),
                        17.0,
                        400
                    )
                }
                view.controller.setCenter(mapScreenViewModel.center)
                view.controller.setZoom(mapScreenViewModel.zoom)
                if (!mapScreenViewModel.loading) {
                    view.overlays.clear()
                    var clickOverlay = MapEventsOverlay(object : MapEventsReceiver {
                        override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                            mapScreenViewModel.selected = false
                            return false
                        }

                        override fun longPressHelper(p: GeoPoint?): Boolean {
                            return false
                        }
                    })
                    view.overlays.add(clickOverlay)
                    if(locationProvider.hasLocationPermissions()){
                        var userMarker = Marker(view)
                        userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                        userMarker.position = GeoPoint(
                            mapScreenViewModel.userLocation.value.latitude,
                            mapScreenViewModel.userLocation.value.longitude
                        )
                        userMarker.icon = ResourcesCompat.getDrawable(
                            view.resources,
                            R.drawable.user_location_marker,
                            null
                        )
                        view.overlays.add(userMarker)
                    }
                    for (sight in mapScreenViewModel.sights) {
                        var marker = Marker(view)
                        var listener = Marker.OnMarkerClickListener { mk, mv ->
                            var point = GeoPoint(sight.latitude, sight.longitude)
                            view.controller.animateTo(point, 18.0, 400)
                            mapScreenViewModel.selectedIndex.longValue = sight.id
                            mapScreenViewModel.selected = true
                            true
                        }
                        marker.setOnMarkerClickListener(listener)
                        marker.position = GeoPoint(sight.latitude, sight.longitude)
                        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                        var drawableId =
                            if (mapScreenViewModel.selected && mapScreenViewModel.selectedIndex.longValue == sight.id) {
                                R.drawable.map_marker_selected
                            } else {
                                R.drawable.map_marker_unselected
                            }
                        marker.icon = ResourcesCompat.getDrawable(
                            view.resources,
                            drawableId,
                            null
                        )
                        view.overlays.add(marker)
                    }
                }
            }
        )
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
                        mapScreenViewModel.animateToCurrentLocationCallback.invoke()
                    }
                })
            }
            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = mapScreenViewModel.selected,
                enter = slideInVertically(initialOffsetY = { h -> h }),
                exit = slideOutVertically(targetOffsetY = { h -> h })
            ) {
                var sightOnMapInfo: SightOnMapInfo =
                    mapScreenViewModel.sights.find { s -> s.id == mapScreenViewModel.selectedIndex.longValue }!!
                SightBox(
                    onButtonClick = { navController.navigate(SightPageScreenClass(sightId = sightOnMapInfo.id)) },
                    sightOnMapInfo = sightOnMapInfo,
                    buttonText = stringResource(R.string.map_screen_open_sight_button_label),
                    closeCallBack = {
                        mapScreenViewModel.selected = false
                    },
                    spacerHeight = 0.dp,
                )
            }
            Spacer(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .height(80.dp)
            )
        }
        AnimatedVisibility(
            visible = mapScreenViewModel.loading, modifier = Modifier
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
                    text = stringResource(R.string.map_screen_loading_map),
                    color = White
                )
            }
        }
    }
}

@Composable
fun SightBox(
    modifier: Modifier = Modifier,
    buttonText: String,
    onButtonClick: () -> Unit,
    closeCallBack: () -> Unit,
    sightOnMapInfo: SightOnMapInfo,
    spacerHeight: Dp
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
                    .clickable(onClick = closeCallBack)
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
                        .background(MaterialTheme.colorScheme.tertiary)
                        .fillMaxWidth(0.4f)
                        .dropShadow(offsetX = 0.dp, offsetY = 0.dp, blur = 16.dp)
                        .clip(
                            RoundedCornerShape(5.dp)
                        )
                        .aspectRatio(180f / 100),
                    contentDescription = "image",
                    model = sightOnMapInfo.imageLink
                )
                Column(
                    modifier = Modifier
                        .weight(weight = 1f, fill = true)
                        .padding(10.dp)
                ) {
                    Text(
                        text = sightOnMapInfo.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                                MediumBlue
                        ),
                        modifier = Modifier
                            .fillMaxWidth(), onClick = onButtonClick
                    ) {
                        Text(
                            text = buttonText,
                            color = White
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .height(spacerHeight)
            )
        }
    }

}

@Composable
fun UserLocationButton(modifier: Modifier = Modifier, onButtonClick: () -> Unit){
    Icon(
        imageVector = ImageVector.vectorResource(R.drawable.location_button_icon),
        tint = White,
        modifier = Modifier
            .clip(
                CircleShape
            )
            .clickable(onClick = onButtonClick)
            .background(color = Black.copy(0.5f))
            .padding(10.dp)
            .size(25.dp),
        contentDescription = null
    )
}
