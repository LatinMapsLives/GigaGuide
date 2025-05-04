package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightInfo

interface SightRepository {
    suspend fun getSightPageInfoById(id: Long): SightInfo?
    suspend fun getAllSightInfos(): List<SightInfo>?
}