package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Rect
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.TilesOverlay
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.R
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ui.theme.MediumBlue
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.ExploreSightScreenViewModel


@Composable
fun ExploreSightScreen(
    exploreSightScreenViewModel: ExploreSightScreenViewModel = hiltViewModel<ExploreSightScreenViewModel>(),
    navController: NavController,
    sightId: Long
) {
    if (exploreSightScreenViewModel.route.value == null && !exploreSightScreenViewModel.loadingRoute.value) {
        exploreSightScreenViewModel.loadRoute()
    }
    var dark = isSystemInDarkTheme()
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
                                exploreSightScreenViewModel.center = getMapCenter(null) as GeoPoint
                                return true
                            }

                            override fun onZoom(event: ZoomEvent?): Boolean {
                                exploreSightScreenViewModel.zoom = zoomLevelDouble
                                return false
                            }
                        }
                    )
                }
            },
            update = { view ->

                view.controller.setCenter(exploreSightScreenViewModel.center)
                view.controller.setZoom(exploreSightScreenViewModel.zoom)

                if (exploreSightScreenViewModel.route.value != null && !exploreSightScreenViewModel.loadingRoute.value) {


                    view.overlays.clear()

                    var line = Polyline()
                    line.width = 5f
                    line.color = MediumBlue.toArgb()
                    for (point in exploreSightScreenViewModel.route.value!!.routePoints) {
                        line.addPoint(GeoPoint(point.latitude, point.longitude))
                    }
                    view.overlays.add(line)

                    for (i in 0..exploreSightScreenViewModel.route.value!!.moments.size - 1) {
                        Log.d(
                            "ROUTE", "NIGGA2"
                        )
                        var m = exploreSightScreenViewModel.route.value!!.moments[i]
                        view.overlays.add(
                            numberedMarker(
                                number = i + 1,
                                point = GeoPoint(m.latitude, m.longitude),
                                selected = exploreSightScreenViewModel.selected.value && exploreSightScreenViewModel.selectedMomentIndex.intValue == i,
                                mapView = view
                            )
                        )
                    }
                    if(exploreSightScreenViewModel.needToAnimate){
                        exploreSightScreenViewModel.needToAnimate = false
                        var mapPoint = exploreSightScreenViewModel.route.value!!.routePoints[0]
                        view.controller.animateTo(GeoPoint(mapPoint.latitude, mapPoint.longitude), 20.0, 400)
                    }
                }
            }
        )
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
    }
}

fun numberedMarker(number: Int, point: GeoPoint, selected: Boolean, mapView: MapView): Marker {
    var marker = Marker(mapView)
    marker.position = point
    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
    var drawableId =
        if (selected) {
            R.drawable.moment_marker_selected
        } else {
            R.drawable.moment_marker_unselected
        }

    val drawable = ResourcesCompat.getDrawable(
        mapView.resources,
        drawableId,
        null
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

@Composable
fun MomentBox() {

}