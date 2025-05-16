package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.FavoritesRepository

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(private val favoritesRepository: FavoritesRepository): ViewModel() {

    var isAuthorized = mutableStateOf(true)
    var favoriteSightTourThumbnails = mutableStateListOf<SightTourThumbnail>()
    var loading = mutableStateOf(false)
    var needToLoad = mutableStateOf(true)

    fun loadFavorites() {
        loading.value = true;
        viewModelScope.launch {
            var sights: List<SightTourThumbnail> = mutableListOf()
            favoriteSightTourThumbnails.clear()
            favoriteSightTourThumbnails.addAll(sights);
            loading.value = false;
            needToLoad.value = false;
        }
    }

}