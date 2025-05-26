package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit

import android.util.Log
import retrofit2.Response
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.SightAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.PreviewSightDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.SightDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightSearchResult
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository

class SightRepositoryRetrofit(private val sightAPI: SightAPI) : SightRepository {

    override suspend fun getSightInfoById(id: Long): SightInfo? {
        var call = sightAPI.getSightById(id)
        var response: Response<SightDTO> = call.execute()
        return if (response.isSuccessful) {
            response.body().let {
                SightInfo(
                    id = it!!.id,
                    name = it.name,
                    description = it.description,
                    time = 30,
                    imageLink = ServerUtils.imageLink(it.imagePath)
                )
            }
        } else {
            null
        }
    }

    override suspend fun getAllSightInfos(): List<SightInfo>? {
        var response: Response<List<SightDTO>> = sightAPI.getAllSights().execute()
        return if (response.isSuccessful) {
            response.body()!!.map { dto ->
                SightInfo(
                    id = dto.id,
                    name = dto.name,
                    description = dto.description,
                    time = 30,
                    imageLink = ServerUtils.imageLink(dto.imagePath)
                )
            }
        } else {
            null
        }
    }

    override suspend fun getAllSightInfosByTourId(tourId: Long): List<SightInfo>? {
        return getAllSightInfos()
    }

    override suspend fun search(name: String): List<SightSearchResult>? {
        var response: Response<List<PreviewSightDTO>> = sightAPI.searchSights(name).execute()
        return if (response.isSuccessful) {
            response.body()!!.map { dto ->
                SightSearchResult(
                    id = dto.id,
                    name = dto.name,
                    imageLink = dto.imagePath,
                    latitude = dto.latitude,
                    longitude = dto.longitude,
                    rating = dto.rating
                )
            }
        } else {
            null
        }
    }

}