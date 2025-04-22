package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import kotlinx.coroutines.delay
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightTourThumbnail

interface SightThumbnailRepository {
    suspend fun getClosestSights(): List<SightTourThumbnail>
}