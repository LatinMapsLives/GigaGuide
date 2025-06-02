package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.moment.MomentInfo

interface MomentRepository {
    suspend fun getSightMoments(sightId: Long, language: String): List<MomentInfo>?
}