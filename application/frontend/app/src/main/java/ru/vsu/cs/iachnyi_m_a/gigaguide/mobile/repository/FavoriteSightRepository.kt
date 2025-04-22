package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import kotlinx.coroutines.delay
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightTourThumbnail

interface FavoriteSightRepository {
    suspend fun getFavorites(): List<SightTourThumbnail>
}