package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import kotlinx.coroutines.delay
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightTourThumbnail

class FavoriteSightsRepository {
    suspend fun getFavorites(): List<SightTourThumbnail> {
        delay(2000)
        return listOf()
    }
}