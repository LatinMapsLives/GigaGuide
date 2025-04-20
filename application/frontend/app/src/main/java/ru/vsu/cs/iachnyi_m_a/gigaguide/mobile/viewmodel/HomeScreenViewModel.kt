package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightThumbnailRepository

class HomeScreenViewModel : ViewModel() {

    val sightRepository = SightThumbnailRepository()
    var closestTours = mutableStateListOf<SightTourThumbnail>()
    var loading = mutableStateOf<Boolean>(false)

    fun loadClosestTours(){
        loading.value = true;
        viewModelScope.launch {
            var sights: List<SightTourThumbnail> = sightRepository.getClosestSights();
            closestTours.clear()
            closestTours.addAll(sights);
            loading.value = false;
        }
    }

}