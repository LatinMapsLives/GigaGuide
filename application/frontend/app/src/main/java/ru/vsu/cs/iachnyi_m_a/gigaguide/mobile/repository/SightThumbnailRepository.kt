package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import kotlinx.coroutines.delay
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightTourThumbnail

class SightThumbnailRepository {
    suspend fun getClosestSights(): List<SightTourThumbnail> {
        delay(1000)
        return listOf(
            SightTourThumbnail(0, "Первомайский сад", 4.8f, 5.5f),
            SightTourThumbnail(1, "Парк \"Орлёнок\"", 4.8f, 5.5f),
        )
    }
}