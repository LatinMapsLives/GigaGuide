package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail

interface FavoriteSightRepository {
    suspend fun getFavorites(): List<SightTourThumbnail>
}