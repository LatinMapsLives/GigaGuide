package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import kotlinx.coroutines.delay
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightTourThumbnail

class SightRepository {
    suspend fun getClosestSights(): List<SightTourThumbnail>{
        delay(2000)
        return listOf(
            SightTourThumbnail("Sight1", "ddasd", 4.8f, 5.5f),
            SightTourThumbnail("sight2", "dasdsas", 4.8f, 5.5f),
            SightTourThumbnail("sight3", "dasadasdsas", 4.8f, 5.5f)
        )
    }
}