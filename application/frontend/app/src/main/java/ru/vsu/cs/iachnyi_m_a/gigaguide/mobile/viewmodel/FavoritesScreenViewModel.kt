package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore.DataStoreManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.FavoritesRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.TourRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils

@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val sightRepository: SightRepository,
    private val tourRepository: TourRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    var isAuthorized = mutableStateOf(false)
    var favoriteSightThumbnails = mutableStateListOf<SightTourThumbnail>()
    var favoriteTourThumbnails = mutableStateListOf<SightTourThumbnail>()
    var loading = mutableStateOf(false)
    var needToLoad = mutableStateOf(true)

    fun loadFavorites() {
        loading.value = true;
        viewModelScope.launch {
            var token = dataStoreManager.getJWT()
            if (token == null) {
                isAuthorized.value = false;
                loading.value = false;
            } else {
                isAuthorized.value = true;
                var sights: MutableList<SightTourThumbnail> = mutableListOf<SightTourThumbnail>()
                var tours: MutableList<SightTourThumbnail> = mutableListOf<SightTourThumbnail>()

                favoriteSightThumbnails.clear()
                favoriteTourThumbnails.clear()
                var favorites = ServerUtils.executeNetworkCall { favoritesRepository.getFavorites(token) }
                favorites?.let {
                    for (id in favorites.sightIds) {
                        var sight = ServerUtils.executeNetworkCall { sightRepository.getSightInfoById(id.toLong()) }
                        sight?.let {
                            sights.add(
                                SightTourThumbnail(
                                    sightId = sight.id,
                                    name = sight.name,
                                    rating = 4.5f,
                                    proximity = 4.5f,
                                    imageLink = sight.imageLink
                                )
                            )
                        }
                    }
                    for (id in favorites.tourIds) {
                        var tour = ServerUtils.executeNetworkCall { tourRepository.getTourInfoById(id.toLong()) }
                        tour?.let {
                            tours.add(
                                SightTourThumbnail(
                                    sightId = tour.id,
                                    name = tour.name,
                                    rating = 4.5f,
                                    proximity = 4.5f,
                                    imageLink = tour.imageLink
                                )
                            )
                        }
                    }
                }
                favoriteSightThumbnails.addAll(sights);
                favoriteTourThumbnails.addAll(tours);
                loading.value = false;
                needToLoad.value = false;
            }
        }
    }

}