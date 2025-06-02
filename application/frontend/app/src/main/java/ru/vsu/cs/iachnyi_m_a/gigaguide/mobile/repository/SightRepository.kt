package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightSearchResult
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightInfo

interface SightRepository {
    suspend fun getSightInfoById(id: Long, language: String): SightInfo?
    suspend fun search(string: String, language: String): List<SightSearchResult>?
}