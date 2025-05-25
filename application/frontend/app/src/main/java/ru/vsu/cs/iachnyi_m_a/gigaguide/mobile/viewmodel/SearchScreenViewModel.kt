package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

            var sightInfos: List<SightInfo>? = ServerUtils.executeNetworkCall { sightRepository.search(searchBarValue.trim()) }
            if (sightInfos != null) {
                sightResult.addAll(sightInfos.map { si ->
                    SightTourThumbnail(
                        sightId = si.id,
                        name = si.name,
                        rating = Random(13471407342).nextInt(40, 51) / 10f,
                        proximity = Random(13471407342).nextInt(2, 20) / 10f,
                        imageLink = si.imageLink
                    )
                });
            }

            var tourInfos: List<TourInfo>? = ServerUtils.executeNetworkCall { tourRepository.getAllTourInfos() }
            if (tourInfos != null) {
                tourResult.addAll(tourInfos.map { ti ->
                    SightTourThumbnail(
                        sightId = ti.id,
                        name = ti.name,
                        rating = Random(13471407342).nextInt(40, 51) / 10f,
                        proximity = Random(13471407342).nextInt(2, 20) / 10f,
                        imageLink = ti.imageLink
                    )
                });
            }
            loading = false
        }
    }
}