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
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore.DataStoreManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.FavoritesList
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.FavoritesRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.MomentRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.review.SightReviewRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.LocaleManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils

@HiltViewModel
class SightPageScreenViewModel @Inject constructor(
    private val momentRepository: MomentRepository,
    private val sightRepository: SightRepository,
    private val favoritesRepository: FavoritesRepository,
    private val sightReviewRepository: SightReviewRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    var sightId = -1L
    var sight = mutableStateOf<SightInfo?>(null)
    var momentNames = mutableStateListOf<String>()
    var loading = mutableStateOf<Boolean>(false)
    var loadingFavorite = true;
    var inFavorite = mutableStateOf<Boolean>(false)
    var token: String? = null
    var reviewCount by mutableIntStateOf(0)
    var rating by mutableFloatStateOf(0f)


    fun loadSight() {
        viewModelScope.launch {
            loading.value = true
            var loadedSight: SightInfo? = ServerUtils.executeNetworkCall { sightRepository.getSightInfoById(sightId, LocaleManager.currentLanguage) }
            sight.value = loadedSight
            momentNames.clear()
            var loadedMoments = ServerUtils.executeNetworkCall { momentRepository.getSightMoments(sightId, LocaleManager.currentLanguage) }
            if (loadedMoments != null) {
                momentNames.addAll(loadedMoments.map { m -> m.name })
            }
            var loadedReviews = ServerUtils.executeNetworkCall { sightReviewRepository.getAllReviews("", sightId.toInt()) }
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
            inFavorite.value = favorites != null && favorites.sightIds.contains(sightId.toInt())
            loadingFavorite = false
        }
    }

    fun addToFavorite(){
        loadingFavorite = true
        viewModelScope.launch {
            loadingFavorite = true;
            var added: Boolean? = ServerUtils.executeNetworkCall { token?.let { favoritesRepository.addSightToFavorites(it, sightId) } }
            inFavorite.value = added != null && added
            loadingFavorite = false
        }
    }

    fun deleteFromFavorite(){
        loadingFavorite = true
        viewModelScope.launch {
            loadingFavorite = true;
            var deleted: Boolean? = ServerUtils.executeNetworkCall { token?.let {favoritesRepository.deleteSightFromFavorites(it, sightId)  } }
            if(deleted == null){
                inFavorite.value = false
            } else {
                inFavorite.value = !deleted
            }
            loadingFavorite = false
        }
    }
}
