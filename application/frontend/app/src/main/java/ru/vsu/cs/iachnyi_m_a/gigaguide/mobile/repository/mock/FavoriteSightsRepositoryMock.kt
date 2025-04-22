package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.mock

import kotlinx.coroutines.delay
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.FavoriteSightRepository

class FavoriteSightsRepositoryMock: FavoriteSightRepository{
    override suspend fun getFavorites(): List<SightTourThumbnail> {
        delay(1000)
        return listOf()
    }
}