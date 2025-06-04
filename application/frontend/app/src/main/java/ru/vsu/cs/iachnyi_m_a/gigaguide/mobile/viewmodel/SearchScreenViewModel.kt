package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore.DataStoreManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightSearchResult
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.TourRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.LocaleManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils
import javax.inject.Inject
import kotlin.math.pow

enum class SortingOptions {
    NONE,
    RATING,
    REMOTENESS
}

enum class FilterOptions {
    NONE,
    HISTORICAL,
    LEISURE
}


@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val sightRepository: SightRepository,
    private val tourRepository: TourRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    var searchResult = mutableStateListOf<SightTourThumbnail>()
    var loading by mutableStateOf(false)

    var searchSights by mutableStateOf(true)
    var searchTours by mutableStateOf(true)
    var minDuration by mutableIntStateOf(0)
    var maxDuration by mutableIntStateOf(120)
    var minDistance by mutableDoubleStateOf(0.0)
    var maxDistance by mutableDoubleStateOf(100.0)

    var sortingOptions by mutableStateOf(SortingOptions.NONE)
    var filterOptions by mutableStateOf(FilterOptions.NONE)
    var historicalOption = ""
    var leisureOption = ""
    var searchBarValue by mutableStateOf("")

    fun loadSearchResult() {

        loading = true

        viewModelScope.launch {
            searchResult.clear()

            if(searchSights) {
                var sightInfos: List<SightSearchResult>? =
                    ServerUtils.executeNetworkCall {
                        sightRepository.search(
                            searchBarValue.trim(),
                            LocaleManager.currentLanguage
                        )
                    }
                if (sightInfos != null) {

                    searchResult.addAll(sightInfos.map {
                        SightTourThumbnail(
                            isTour = false,
                            sightId = it.id.toLong(),
                            name = it.name,
                            rating = it.rating,
                            proximity = proximityKm(dataStoreManager.getLastLocation().latitude, dataStoreManager.getLastLocation().longitude, it.latitude.toDouble(), it.longitude.toDouble()),
                            imageLink = it.imageLink
                        )
                    });
                }
            }

            if(searchTours) {
                var category: String? = null
                if(filterOptions == FilterOptions.HISTORICAL) category = historicalOption
                if(filterOptions == FilterOptions.LEISURE) category = leisureOption
                var tourInfos: List<SightTourThumbnail>? =
                    ServerUtils.executeNetworkCall {
                        tourRepository.searchFilterTours(
                            name = searchBarValue.trim(),
                            language = LocaleManager.currentLanguage,
                            minDuration = minDuration,
                            maxDuration = maxDuration,
                            minDistance = minDistance,
                            maxDistance = maxDistance,
                            latitude = dataStoreManager.getLastLocation().latitude,
                            longitude = dataStoreManager.getLastLocation().longitude,
                            category = category
                        )
                    }
                if (tourInfos != null) {
                    searchResult.addAll(tourInfos);
                }
            }

            if (sortingOptions == SortingOptions.RATING) {
                searchResult.sortBy { -it.rating }
            } else {
                searchResult.sortBy { it.proximity }
            }

            loading = false
        }
    }
}