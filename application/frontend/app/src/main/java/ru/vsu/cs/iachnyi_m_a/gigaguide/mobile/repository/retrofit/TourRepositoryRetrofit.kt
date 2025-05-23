package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit

import retrofit2.Response
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.TourAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.TourDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper.TourDTOMapper
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.TourInfo
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
}