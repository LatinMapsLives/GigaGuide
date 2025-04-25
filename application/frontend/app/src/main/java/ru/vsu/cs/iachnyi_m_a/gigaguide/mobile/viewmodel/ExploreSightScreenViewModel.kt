package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightRoute
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.RouteRepository

@HiltViewModel
class ExploreSightScreenViewModel @Inject constructor(private var sightRouteRepository: RouteRepository): ViewModel() {

    var center = GeoPoint(51.670859, 39.210282)
    var sightId: Long = -1
    var zoom = 16.0
    var loadingRoute = mutableStateOf(false)
    var route = mutableStateOf<SightRoute?>(null);
    var selected = mutableStateOf(false)
    var selectedMomentIndex = mutableIntStateOf(-1)
    var needToAnimate = false

    fun loadRoute(){
        viewModelScope.launch {
            loadingRoute.value = true
            var loadedRoute = sightRouteRepository.getSightRoute(0)
            route.value = loadedRoute
            loadingRoute.value = false
            needToAnimate = true
        }
    }
}