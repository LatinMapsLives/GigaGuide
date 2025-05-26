package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightSearchResult
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.TourInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.TourRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class SearchScreenViewModel @Inject constructor(private val sightRepository: SightRepository,
    private val tourRepository: TourRepository): ViewModel() {

    var sightResult = mutableStateListOf<SightTourThumbnail>()
    var tourResult = mutableStateListOf<SightTourThumbnail>()
    var loading by mutableStateOf(false)
    var searchBarValue by mutableStateOf("")

    fun loadSearchResult(){

        loading = true

        viewModelScope.launch {
            sightResult.clear()
            tourResult.clear()

            var sightInfos: List<SightSearchResult>? = ServerUtils.executeNetworkCall { sightRepository.search(searchBarValue.trim()) }
            if (sightInfos != null) {
                sightResult.addAll(sightInfos.map { SightTourThumbnail(sightId = it.id.toLong(), name = it.name, rating = it.rating, proximity = 0f, imageLink = it.imageLink) });
            }

            var tourInfos: List<SightTourThumbnail>? = ServerUtils.executeNetworkCall { tourRepository.searchTours(searchBarValue.trim()) }
            if(tourInfos == null) Log.e("SEARCH", "NIGGA")
            if (tourInfos != null) {
                tourResult.addAll(tourInfos.map { ti ->
                    SightTourThumbnail(
                        sightId = ti.sightId,
                        name = ti.name,
                        rating = ti.rating,
                        proximity = 0f,
                        imageLink = ti.imageLink
                    )
                });
            }
            loading = false
        }
    }
}