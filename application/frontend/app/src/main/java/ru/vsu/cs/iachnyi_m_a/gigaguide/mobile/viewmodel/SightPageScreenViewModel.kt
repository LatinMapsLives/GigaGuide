package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository

class SightPageScreenViewModel(): ViewModel() {

    var sightRepository = SightRepository();
    var sightId = mutableLongStateOf(-1)
    var sight = mutableStateOf<SightInfo?>(null)
    var loading = mutableStateOf<Boolean>(false)

    fun loadSight(){
        viewModelScope.launch {
            loading.value = true
            var loadedSight:SightInfo = sightRepository.getSightById(sightId.longValue)
            sight.value = loadedSight
            loading.value = false
        }
    }
}