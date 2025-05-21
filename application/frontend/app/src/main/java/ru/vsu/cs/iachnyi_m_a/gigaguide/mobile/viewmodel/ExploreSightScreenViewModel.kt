package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.MapPoint
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.moment.MomentOnMap
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.MapRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.MomentRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils

@HiltViewModel
class ExploreSightScreenViewModel @Inject constructor(
    private var momentRepository: MomentRepository,
    private var mapRepository: MapRepository
) :
    ViewModel() {

    var center = GeoPoint(51.670859, 39.210282)
    var sightId: Long = -1
    var zoom = 16.0
    var loadingRoute = mutableStateOf(false)
    var route = mutableStateListOf<MapPoint>();
    var momentOnMaps = mutableStateListOf<MomentOnMap>()
    var selected = mutableStateOf(false)
    var selectedMomentIndex = mutableIntStateOf(-1)
    var needToAnimateTo: GeoPoint? = null

    var playerIsLoading = mutableStateOf(true)
    lateinit var player: ExoPlayer
    var currentTrackDurationMs = mutableLongStateOf(-1)
    var currentTrackPositionMs = mutableLongStateOf(0)

    fun currentMoment(): MomentOnMap? {
        return momentOnMaps[selectedMomentIndex.intValue]
    }

    suspend fun loadRoute() {
        loadingRoute.value = true

        momentOnMaps.clear()
        var loadedMoments = ServerUtils.executeNetworkCall { momentRepository.getSightMoments(sightId) }
        if (loadedMoments != null) {
            for (momentInfo in loadedMoments) {
                var mapPoint = ServerUtils.executeNetworkCall { mapRepository.getCoordinatesOfMoment(momentInfo.id) }
                if (mapPoint != null) {
                    momentOnMaps.add(
                        MomentOnMap(
                            id = momentInfo.id,
                            name = momentInfo.name,
                            latitude = mapPoint.latitude,
                            longitude = mapPoint.longitude,
                            audioLink = ServerUtils.audioGuideLink(momentInfo.id),
                            imageLink = momentInfo.imagePath
                        )
                    )
                }
            }
        }

        var loadedRoute = momentOnMaps.map { m -> MapPoint(m.latitude, m.longitude) }

        route.clear()
        route.addAll(loadedRoute)
        loadingRoute.value = false

    }

    fun launchPositionUpdateLoop() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                currentTrackPositionMs.longValue = player.currentPosition
            }
        }
    }
}