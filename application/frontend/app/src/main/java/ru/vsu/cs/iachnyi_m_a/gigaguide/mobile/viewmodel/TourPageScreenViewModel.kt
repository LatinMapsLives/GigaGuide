package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore.DataStoreManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.FavoritesList
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.TourInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.FavoritesRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.TourRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.review.TourReviewRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils
import javax.inject.Inject

@HiltViewModel
class TourPageScreenViewModel @Inject constructor(

    private val tourRepository: TourRepository,
    private val sightRepository: SightRepository,
    private val favoritesRepository: FavoritesRepository,
    private val dataStoreManager: DataStoreManager,
    private val tourReviewRepository: TourReviewRepository): ViewModel() {

    var tourId = -1L
    var tour = mutableStateOf<TourInfo?>(null)
    var sightThumbnails = mutableStateListOf<SightTourThumbnail>()
    var loading = mutableStateOf<Boolean>(false)
    var loadingFavorite = true;
    var inFavorite = mutableStateOf<Boolean>(false)
    var token: String? = null
    var reviewCount by mutableIntStateOf(0)
    var rating by mutableFloatStateOf(0f)

    fun loadTour() {
        viewModelScope.launch {
            loading.value = true
            var loadedTour: TourInfo? = ServerUtils.executeNetworkCall { tourRepository.getTourInfoById(tourId) }
            if(loadedTour == null) {
                return@launch
            }
            tour.value = loadedTour
            sightThumbnails.clear()
            sightThumbnails.addAll(loadedTour.sights)

            var loadedReviews = ServerUtils.executeNetworkCall { tourReviewRepository.getAllReviews("", tourId.toInt()) }

            if(loadedReviews!= null){
                reviewCount = loadedReviews.otherReviews.size
                var avg = if(reviewCount == 0) 0f else (loadedReviews.otherReviews.sumOf { r -> r.rating } * 1f / reviewCount)
                rating = (avg * 10).toInt() / 10f
            }

            loading.value = false
        }
    }

    fun loadFavoriteData() {
        loadingFavorite = true
        viewModelScope.launch {
            token = dataStoreManager.getJWT()
            loadingFavorite = true;
            var favorites: FavoritesList? = ServerUtils.executeNetworkCall { token?.let { favoritesRepository.getFavorites(it) } }
            Log.e("FAV", favorites.toString())
            inFavorite.value = favorites != null && favorites.tourIds.contains(tourId.toInt())
            loadingFavorite = false
        }
    }

    fun addToFavorite(){
        loadingFavorite = true
        viewModelScope.launch {
            loadingFavorite = true;
            var added: Boolean? = ServerUtils.executeNetworkCall {token?.let { favoritesRepository.addTourToFavorites(it, tourId) }}
            inFavorite.value = added != null && added
            loadingFavorite = false
        }
    }

    fun deleteFromFavorite(){
        loadingFavorite = true
        viewModelScope.launch {
            loadingFavorite = true;
            var deleted: Boolean? = ServerUtils.executeNetworkCall { token?.let {favoritesRepository.deleteTourFromFavorites(it, tourId)  } }
            if(deleted == null){
                inFavorite.value = false
            } else {
                inFavorite.value = !deleted
            }
            loadingFavorite = false
        }
    }
}