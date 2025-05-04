package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit

import kotlinx.coroutines.delay
import retrofit2.Response
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.SightAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.SightDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.SightRepository

class SightRepositoryRetrofit(private val sightAPI: SightAPI) : SightRepository {

    override suspend fun getSightPageInfoById(id: Long): SightInfo? {
        delay(500)
        var call = sightAPI.getSightById(id)
        var response: Response<SightDTO> = call.execute()
        return if (response.isSuccessful) {
            response.body().let {
                SightInfo(
                    id = it!!.id,
                    name = it.name,
                    description = it.description,
                    time = 30,
                    imageLink = "http://192.168.1.84:8080/api/tour-sight/image?fileName=${it.imagePath}"
                )
            }
        } else {
            null
        }
    }

    override suspend fun getAllSightInfos(): List<SightInfo>? {
        delay(500)
        var response: Response<List<SightDTO>> = sightAPI.getAllSights().execute()
        return if (response.isSuccessful) {
            response.body()!!.map { dto ->
                SightInfo(
                    id = dto.id,
                    name = dto.name,
                    description = dto.description,
                    time = 30,
                    imageLink = "http://192.168.1.84:8080/api/tour-sight/image?fileName=${dto.imagePath}"
                )
            }
        } else {
            null
        }
    }

}