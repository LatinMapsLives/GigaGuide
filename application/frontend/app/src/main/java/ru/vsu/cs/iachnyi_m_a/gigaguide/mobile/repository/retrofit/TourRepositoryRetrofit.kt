package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit

import retrofit2.Response
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.TourAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.PreviewTourDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.TourDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper.TourDTOMapper
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.TourInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.TourRepository
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.viewmodel.proximityKm

class TourRepositoryRetrofit(private val tourAPI: TourAPI) : TourRepository {

    override suspend fun getTourInfoById(id: Long, language: String): TourInfo? {
        var call = tourAPI.getTourById(id, language)
        var response: Response<TourDTO> = call.execute()
        return if (response.isSuccessful) {
            TourDTOMapper().map(response.body()!!)
        } else {
            null
        }
    }

    override suspend fun searchFilterTours(
        name: String,
        language: String,
        userLatitude: Double,
        userLongitude: Double,
        minDuration: Int?,
        maxDuration: Int?,
        minDistance: Double?,
        maxDistance: Double?,
        category: String?
    ): List<SightTourThumbnail>? {

        var params: MutableMap<String, String> = HashMap()
        params.put("query", name)
        params.put("language", language)
        minDuration?.let { params.put("minDuration", minDuration.toString()) }
        maxDuration?.let { params.put("maxDuration", maxDuration.toString()) }
        minDistance?.let { params.put("minDistance", minDistance.toString()) }
        maxDistance?.let { params.put("maxDistance", maxDistance.toString()) }
        category?.let { params.put("category", category) }
        var call = tourAPI.searchTours(params)
        var response: Response<List<PreviewTourDTO>> = call.execute()
        return if (response.isSuccessful) {
            response.body()!!.map { dto ->
                SightTourThumbnail(
                    isTour = true,
                    sightId = dto.id.toLong(),
                    rating = dto.rating,
                    name = dto.name,
                    proximity = proximityKm(
                        userLatitude,
                        userLongitude,
                        dto.latitude,
                        dto.longitude
                    ),
                    imageLink = dto.imagePath
                )
            }
        } else {
            null
        }
    }
}