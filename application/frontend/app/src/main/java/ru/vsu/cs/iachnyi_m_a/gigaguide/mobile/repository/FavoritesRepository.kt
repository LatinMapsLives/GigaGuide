package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.FavoritesList

interface FavoritesRepository {
    suspend fun getFavorites(token: String): FavoritesList?
    suspend fun addSightToFavorites(token: String, sightId: Long): Boolean?
    suspend fun deleteSightFromFavorites(token: String, sightId: Long): Boolean?
    suspend fun addTourToFavorites(token: String, sightId: Long): Boolean?
    suspend fun deleteTourFromFavorites(token: String, sightId: Long): Boolean?
}