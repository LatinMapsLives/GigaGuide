package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ServerUtils
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore.DataStoreManager
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.FavoritesRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository
import java.net.ConnectException
import java.net.SocketTimeoutException

@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val sightRepository: SightRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    var isAuthorized = mutableStateOf(false)
    var favoriteSightTourThumbnails = mutableStateListOf<SightTourThumbnail>()
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
                favoriteSightTourThumbnails.clear()
                var favorites = try {
                    withContext(Dispatchers.IO) {
                        favoritesRepository.getFavoriteSights(token)
                    }
                } catch (e: ConnectException) {
                    null
                } catch (e: SocketTimeoutException) {
                    null
                }
                favorites?.let {
                    for (id in favorites.sightIds) {
                        var sight = try {
                            withContext(Dispatchers.IO) {
                                sightRepository.getSightPageInfoById(id.toLong())
                            }
                        } catch (e: ConnectException) {
                            null
                        } catch (e: SocketTimeoutException) {
                            null
                        }
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
                }
                favoriteSightTourThumbnails.addAll(sights);
                loading.value = false;
                needToLoad.value = false;
            }
        }
    }

}