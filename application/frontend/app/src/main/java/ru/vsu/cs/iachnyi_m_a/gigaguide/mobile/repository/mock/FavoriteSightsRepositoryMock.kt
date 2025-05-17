package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.mock

import kotlinx.coroutines.delay
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.FavoritesList
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.FavoritesRepository

class FavoriteSightsRepositoryMock: FavoritesRepository{

    override suspend fun getFavorites(token: String): FavoritesList {
        TODO("Not yet implemented")
    }

    override suspend fun addSightToFavorites(
        token: String,
        sightId: Long
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSightFromFavorites(
        token: String,
        sightId: Long
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun addTourToFavorites(
        token: String,
        sightId: Long
    ): Boolean? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTourFromFavorites(
        token: String,
        sightId: Long
    ): Boolean? {
        TODO("Not yet implemented")
    }
}