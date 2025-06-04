package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.MapPoint

interface MapRepository {
    suspend fun getCoordinatedOfSight(sightId: Long): MapPoint?
    suspend fun getCoordinatesOfMoment(sightId: Long): MapPoint?
    suspend fun getRouteOfSight(sightId: Long): List<MapPoint>?
    suspend fun getRouteOfTour(tourId: Long): List<MapPoint>?
}