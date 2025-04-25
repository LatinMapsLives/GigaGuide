package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightRoute

interface RouteRepository {
    suspend fun getSightRoute(sightId: Long): SightRoute
}