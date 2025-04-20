package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@Composable
fun MapScreen() {
    var geoPoint by remember { mutableStateOf(GeoPoint(51.672, 39.1843)) }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            // Creates the view
            MapView(context).apply {
                setMultiTouchControls(true)
                controller.setZoom(16)
                // Do anything that needs to happen on the view init here
                // For example set the tile source or add a click listener
                setTileSource(TileSourceFactory.MAPNIK)
                Configuration.getInstance().userAgentValue = "GigaGuide"
                setOnClickListener {
                    TODO("Handle click here")
                }
            }
        },
        update = { view ->
            // Code to update or recompose the view goes here
            // Since geoPoint is read here, the view will recompose whenever it is updated
            view.controller.setCenter(geoPoint)
        }
    )
}