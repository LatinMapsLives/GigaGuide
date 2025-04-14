package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.FavoriteSightsRepository

class FavoriteScreenViewModel : ViewModel() {

    var isAuthorized = mutableStateOf(true)
    var favoriteSightTourThumbnails = mutableStateListOf<SightTourThumbnail>()
    var loading = mutableStateOf(false)
    var needToLoad = mutableStateOf(true)
    var favoriteRepository: FavoriteSightsRepository = FavoriteSightsRepository();

    fun loadFavorites() {
        loading.value = true;
        viewModelScope.launch {
            var sights: List<SightTourThumbnail> = favoriteRepository.getFavorites();
            favoriteSightTourThumbnails.clear()
            favoriteSightTourThumbnails.addAll(sights);
            loading.value = false;
            needToLoad.value = false;
        }
    }

}