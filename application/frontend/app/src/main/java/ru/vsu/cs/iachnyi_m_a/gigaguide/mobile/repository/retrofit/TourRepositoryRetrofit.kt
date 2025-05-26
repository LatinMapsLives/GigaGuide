package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit

import android.util.Log
import retrofit2.Response
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.TourAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.TourDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.PreviewTourDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper.TourDTOMapper
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.TourInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.TourRepository

class TourRepositoryRetrofit(private val tourAPI: TourAPI): TourRepository {
    override suspend fun getAllTourInfos(): List<TourInfo>? {
        var response: Response<List<TourDTO>> = tourAPI.getAllTours().execute()
        var mapper = TourDTOMapper()
        return if (response.isSuccessful) {
            response.body()!!.map { dto -> mapper.map(dto) }
        } else {
            null
        }
    }

    override suspend fun getTourInfoById(id: Long): TourInfo? {
        var call = tourAPI.getTourById(id)
        var response: Response<TourDTO> = call.execute()
        return if (response.isSuccessful) {
            TourDTOMapper().map(response.body()!!)
        } else {
            null
        }
    }

    override suspend fun searchTours(name: String): List<SightTourThumbnail>? {
        var call = tourAPI.searchTours(name)
        var response: Response<List<PreviewTourDTO>> = call.execute()
        Log.e("SEARCH", call.request().url.toString())
        return if (response.isSuccessful) {
            response.body()!!.map { dto -> SightTourThumbnail(sightId = dto.id.toLong(), rating = dto.rating, name = dto.name, proximity = 0f, imageLink = dto.imagePath) }
        } else {
            null
        }
    }
}