package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.MapPoint
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.moment.MomentOnMap
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightOnMapInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.MapRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.MomentRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.Pancake
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils

@HiltViewModel
class ExploreTourScreenViewModel @Inject constructor(
    private var momentRepository: MomentRepository,
    private var sightRepository: SightRepository,
    private var mapRepository: MapRepository
) :
    ViewModel() {

    var center = GeoPoint(51.670859, 39.210282)
    var needToAnimateTo: GeoPoint? = null
    var zoom = 16.0

    var tourId: Long = -1
    var tourRoute = mutableStateListOf<MapPoint>()
    var loadingTour = mutableStateOf(false)

    var sightsOnMapInfos = mutableStateListOf<SightOnMapInfo>()
    var selectedSightIndex = mutableIntStateOf(-1)
    var sightRoutes = mutableStateListOf<List<MapPoint>>()

    var momentOnMaps = mutableStateListOf<List<MomentOnMap>>()

    var selectedMomentIndex = mutableIntStateOf(-1)

    var momentIsSelected by mutableStateOf(false)
    var sightIsSelected by mutableStateOf(false)
    var exploringSight = mutableStateOf(false)

    var indexesMap = mutableListOf<List<Int>>()

    var playerIsLoading = mutableStateOf(true)
    lateinit var player: ExoPlayer
    var currentTrackDurationMs = mutableLongStateOf(-1)
    var currentTrackPositionMs = mutableLongStateOf(0)

    fun currentMoment(): MomentOnMap? {
        return momentOnMaps[selectedSightIndex.intValue][selectedMomentIndex.intValue]
    }

    fun loadTour(){
        viewModelScope.launch {
            var i = 0
            loadingTour.value = true
            var loadedSights = ServerUtils.executeNetworkCall { sightRepository.getAllSightInfosByTourId(tourId) }
            Log.e("TOUR", loadedSights.toString())
            sightsOnMapInfos.clear()
            sightRoutes.clear()
            tourRoute.clear()
            momentOnMaps.clear()
            indexesMap.clear()
            if (loadedSights != null) {
                for (sightInfo in loadedSights) {
                    var sightMapPoint = ServerUtils.executeNetworkCall {  mapRepository.getCoordinatedOfSight(sightInfo.id)}
                    if (sightMapPoint != null){
                        sightsOnMapInfos.add(
                            SightOnMapInfo(
                                id = sightInfo.id,
                                name = sightInfo.name,
                                latitude = sightMapPoint.latitude,
                                longitude = sightMapPoint.longitude,
                                sightInfo.imageLink
                            )
                        )
                        tourRoute.add(MapPoint(sightMapPoint.latitude, sightMapPoint.longitude))
                    } else {
                        Pancake.serverError()
                        return@launch
                    }
                    var loadedMoments = ServerUtils.executeNetworkCall { momentRepository.getSightMoments(sightInfo.id) }
                    if (loadedMoments != null) {
                        var indices = mutableListOf<Int>()
                        var momentOnMapsForThisSight = mutableListOf<MomentOnMap>()
                        var thisSightRoute = mutableListOf<MapPoint>()
                        for (momentInfo in loadedMoments) {
                            indices.add(i)
                            i++
                            var momentMapPoint = ServerUtils.executeNetworkCall { mapRepository.getCoordinatesOfMoment(momentInfo.id) }
                            if (momentMapPoint != null) {
                                thisSightRoute.add(momentMapPoint)
                                momentOnMapsForThisSight.add(
                                    MomentOnMap(
                                        id = momentInfo.id,
                                        name = momentInfo.name,
                                        latitude = momentMapPoint.latitude,
                                        longitude = momentMapPoint.longitude,
                                        audioLink = ServerUtils.audioGuideLink(momentInfo.id),
                                        imageLink = momentInfo.imagePath
                                    )
                                )
                                player.addMediaItem(MediaItem.fromUri(ServerUtils.audioGuideLink(momentInfo.id).toUri()))
                            } else {
                                Pancake.serverError()
                                return@launch
                            }
                        }
                        indexesMap.add(indices)
                        sightRoutes.add(thisSightRoute)
                        momentOnMaps.add(momentOnMapsForThisSight)
                    } else {
                        Pancake.serverError()
                        return@launch
                    }
                }
            } else {
                Pancake.serverError()
                return@launch
            }
            player.playWhenReady = false
            player.prepare()
            launchPositionUpdateLoop()
            loadingTour.value = false
        }
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