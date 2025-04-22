package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import kotlinx.coroutines.delay
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightOnMapInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightPageInfo

interface SightRepository {
    suspend fun getSightPageInfoById(id: Long): SightPageInfo
    suspend fun getAllSightOnMapInfos(): List<SightOnMapInfo>
}