package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.TourInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import kotlin.time.Duration

interface TourRepository {
    suspend fun getTourInfoById(id: Long, language: String): TourInfo?
    suspend fun searchFilterTours(name: String, language: String, latitude: Double, longitude: Double, minDuration: Int?, maxDuration: Int?, minDistance: Double?, maxDistance: Double?): List<SightTourThumbnail>?
}