package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit

import retrofit2.Response
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.MapAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.CoordinatesDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.MapPoint
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.MapRepository

class MapRepositoryRetrofit(private val mapAPI: MapAPI): MapRepository {
    override suspend fun getCoordinatedOfSight(sightId: Long): MapPoint? {
        var response: Response<CoordinatesDTO> = mapAPI.getSightCoordinates(sightId).execute()
        return if (response.isSuccessful){
            MapPoint(latitude = response.body()!!.latitude, longitude = response.body()!!.longitude)
        } else {
            null
        }
    }

    override suspend fun getCoordinatesOfMoment(sightId: Long): MapPoint? {
        var response: Response<CoordinatesDTO> = mapAPI.getMomentCoordinates(sightId).execute()
        return if (response.isSuccessful){
            MapPoint(latitude = response.body()!!.latitude, longitude = response.body()!!.longitude)
        } else {
            null
        }
    }

    override suspend fun getRouteOfSight(sightId: Long): List<MapPoint>? {
        return listOf(
            MapPoint(51.67393, 39.211287),
            MapPoint(51.673846, 39.211615),
            MapPoint(51.673811, 39.211592),
            MapPoint(51.673825, 39.211688),
        )
    }

}