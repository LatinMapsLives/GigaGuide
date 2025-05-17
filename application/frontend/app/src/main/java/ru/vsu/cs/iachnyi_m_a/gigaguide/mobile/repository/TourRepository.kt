package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.TourInfo

interface TourRepository {
    suspend fun getAllTourInfos(): List<TourInfo>?
    suspend fun getTourInfoById(id: Long): TourInfo?
}