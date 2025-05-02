package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
import org.osmdroid.views.Projection
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.TilesOverlay
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightOnMapInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation.SightPageScreenClass
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.GigaGuideMobileTheme
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumBlue
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.White
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view.util.dropShadow
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.MapScreenViewModel


@Composable
fun MapScreen(mapScreenViewModel: MapScreenViewModel, navController: NavController) {
    var dark = isSystemInDarkTheme()
    var sights = mapScreenViewModel.sights
    if (sights.isEmpty()) {
        mapScreenViewModel.loadSightsOnMap()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize().testTag("MAP_VIEW"),
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
                view.controller.setCenter(mapScreenViewModel.center)
                view.controller.setZoom(mapScreenViewModel.zoom)
                if (!mapScreenViewModel.loading.value) {
                    view.overlays.clear()
                    var clickOverlay = MapEventsOverlay(object : MapEventsReceiver {
                        override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                            mapScreenViewModel.selected.value = false
                            return false
                        }

                        override fun longPressHelper(p: GeoPoint?): Boolean {
                            return false
                        }
                    })
                    view.overlays.add(clickOverlay)
                    for (sight in sights) {
                        var marker = Marker(view)
                        var listener = Marker.OnMarkerClickListener { mk, mv ->
                            var point = GeoPoint(sight.latitude, sight.longitude)
                            view.controller.animateTo(point, 18.0, 400)
                            mapScreenViewModel.selectedIndex.longValue = sight.id
                            mapScreenViewModel.selected.value = true
                            true
                        }
                        marker.setOnMarkerClickListener(listener)
                        marker.position = GeoPoint(sight.latitude, sight.longitude)
                        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                        var drawableId =
                            if (mapScreenViewModel.selected.value && mapScreenViewModel.selectedIndex.longValue == sight.id) {
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
        if (mapScreenViewModel.selected.value) {
            var sightOnMapInfo: SightOnMapInfo =
                mapScreenViewModel.sights.find { s -> s.id == mapScreenViewModel.selectedIndex.longValue }!!
            SightBox(
                navController = navController,
                mapScreenViewModel = mapScreenViewModel,
                sightOnMapInfo = sightOnMapInfo,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun SightBox(
    modifier: Modifier,
    navController: NavController,
    mapScreenViewModel: MapScreenViewModel,
    sightOnMapInfo: SightOnMapInfo

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
                    .clickable(onClick = {
                        mapScreenViewModel.selected.value = false
                    })
                    .background(color = MaterialTheme.colorScheme.tertiary)
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
                    .height(10.dp),
                imageVector = ImageVector.vectorResource(R.drawable.chevron_down),
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
                    model = "https://vestivrn.ru/media/archive/image/2024/05/LT7zhWmmZ-tD6ilZ4nQcUPkF1S4SIdph.jpg"
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
                            .fillMaxWidth(), onClick = {
                            navController.navigate(SightPageScreenClass(sightId = sightOnMapInfo.id))
                        }) {
                        Text(
                            text = stringResource(R.string.map_screen_open_sight_button_label),
                            color = White
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .height(85.dp)
            )
        }
    }

}
