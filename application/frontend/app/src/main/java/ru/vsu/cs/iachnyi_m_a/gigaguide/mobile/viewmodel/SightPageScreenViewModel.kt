package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore.DataStoreManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.FavoritesList
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.FavoritesRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.MomentRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository
import java.net.ConnectException
import java.net.SocketTimeoutException

@HiltViewModel
class SightPageScreenViewModel @Inject constructor(
    private val momentRepository: MomentRepository,
    private val sightRepository: SightRepository,
    private val favoritesRepository: FavoritesRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    var sightId = -1L
    var sight = mutableStateOf<SightInfo?>(null)
    var momentNames = mutableStateListOf<String>()
    var loading = mutableStateOf<Boolean>(false)
    var loadingFavorite = true;
    var inFavorite = mutableStateOf<Boolean>(false)
    var token: String? = null


    fun loadSight() {
        viewModelScope.launch {
            loading.value = true
            var loadedSight: SightInfo? = try {
                withContext(Dispatchers.IO) {
                    sightRepository.getSightInfoById(sightId)
                }
            } catch (e: ConnectException) {
                null
            } catch (e: SocketTimeoutException) {
                null
            }
            sight.value = loadedSight
            momentNames.clear()
            var loadedMoments = try {
                withContext(Dispatchers.IO) {
                    momentRepository.getSightMoments(sightId)
                }
            } catch (e: ConnectException) {
                null
            } catch (e: SocketTimeoutException) {
                null
            }
            if (loadedMoments != null) {
                momentNames.addAll(loadedMoments.map { m -> m.name })
            }
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
            inFavorite.value = favorites != null && favorites.sightIds.contains(sightId.toInt())
            loadingFavorite = false
        }
    }

    fun addToFavorite(){
        loadingFavorite = true
        viewModelScope.launch {
            loadingFavorite = true;
            var added: Boolean? = try {
                withContext(Dispatchers.IO) {
                    token?.let { favoritesRepository.addSightToFavorites(it, sightId) }
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
                    token?.let {favoritesRepository.deleteSightFromFavorites(it, sightId)  }
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
