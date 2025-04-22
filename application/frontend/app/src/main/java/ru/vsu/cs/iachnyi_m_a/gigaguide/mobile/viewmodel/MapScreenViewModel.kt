package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightOnMapInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.mock.SightRepositoryMock

@HiltViewModel
class  MapScreenViewModel @Inject constructor(private val sightRepository: SightRepository): ViewModel(){

    var center = GeoPoint(51.670859, 39.210282)
    var zoom = 16.0
    var sights = mutableStateListOf<SightOnMapInfo>()
    var loading = mutableStateOf(false)
    var selected = mutableStateOf(false)
    var selectedIndex = mutableLongStateOf(-1)

    fun loadSightsOnMap(){
        viewModelScope.launch {
            loading.value = true
            var loadedSights = sightRepository.getAllSightOnMapInfos()
            sights.clear()
            sights.addAll(loadedSights)
            loading.value = false
        }
    }
}