package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore.DataStoreManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.FavoritesList
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.TourInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.FavoritesRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.TourRepository
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class TourPageScreenViewModel @Inject constructor(

    private val tourRepository: TourRepository,
    private val sightRepository: SightRepository,
    private val favoritesRepository: FavoritesRepository,
    private val dataStoreManager: DataStoreManager): ViewModel() {

    var tourId = -1L
    var tour = mutableStateOf<TourInfo?>(null)
    var sightThumbnails = mutableStateListOf<SightTourThumbnail>()
    var loading = mutableStateOf<Boolean>(false)
    var loadingFavorite = true;
    var inFavorite = mutableStateOf<Boolean>(false)
    var token: String? = null

    fun loadTour() {
        viewModelScope.launch {
            loading.value = true
            var loadedSight: TourInfo? = try {
                withContext(Dispatchers.IO) {
                    tourRepository.getTourInfoById(tourId)
                }
            } catch (e: ConnectException) {
                null
            } catch (e: SocketTimeoutException) {
                null
            }
            tour.value = loadedSight
            sightThumbnails.clear()
//            var loadedMoments = try {
//                withContext(Dispatchers.IO) {
//                    momentRepository.getSightMoments(sightId)
//                }
//            } catch (e: ConnectException) {
//                null
//            } catch (e: SocketTimeoutException) {
//                null
//            }
//            if (loadedMoments != null) {
//                momentNames.addAll(loadedMoments.map { m -> m.name })
//            }
            loading.value = false
        }
    }

    fun loadFavoriteData() {
        loadingFavorite = true
        viewModelScope.launch {
            token = dataStoreManager.getJWT()
            loadingFavorite = true;
            var favorites: FavoritesList? = try {
                withContext(Dispatchers.IO) {
                    token?.let { favoritesRepository.getFavorites(it) }
                }
            } catch (e: ConnectException) {
                null
            } catch (e: SocketTimeoutException) {
                null
            }
            Log.e("FAV", favorites.toString())
            inFavorite.value = favorites != null && favorites.tourIds.contains(tourId.toInt())
            loadingFavorite = false
        }
    }

    fun addToFavorite(){
        loadingFavorite = true
        viewModelScope.launch {
            loadingFavorite = true;
            var added: Boolean? = try {
                withContext(Dispatchers.IO) {
                    token?.let { favoritesRepository.addTourToFavorites(it, tourId) }
                }
            } catch (e: ConnectException) {
                null
            } catch (e: SocketTimeoutException) {
                null
            }
            inFavorite.value = added != null && added
            loadingFavorite = false
        }
    }

    fun deleteFromFavorite(){
        loadingFavorite = true
        viewModelScope.launch {
            loadingFavorite = true;
            var deleted: Boolean? = try {
                withContext(Dispatchers.IO) {
                    token?.let {favoritesRepository.deleteTourFromFavorites(it, tourId)  }
                }
            } catch (e: ConnectException) {
                null
            } catch (e: SocketTimeoutException) {
                null
            }
            if(deleted == null){
                inFavorite.value = false
            } else {
                inFavorite.value = !deleted
            }
            loadingFavorite = false
        }
    }
}