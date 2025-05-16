package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.FavoritesList

interface FavoritesRepository {
    suspend fun getFavoriteSights(token: String): FavoritesList?
    suspend fun addToFavorites(token: String, sightId: Long): Boolean?
    suspend fun deleteFromFavorites(token: String, sightId: Long): Boolean?
}