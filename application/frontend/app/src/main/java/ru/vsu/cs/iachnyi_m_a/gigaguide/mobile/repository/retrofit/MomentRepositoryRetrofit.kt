package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit

import retrofit2.Response
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.MomentAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.MomentDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.moment.MomentInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.MomentRepository

class MomentRepositoryRetrofit(private val momentAPI: MomentAPI) : MomentRepository {
    override suspend fun getSightMoments(sightId: Long): List<MomentInfo>? {
        var response: Response<List<MomentDTO>> = momentAPI.getAllMoments(sightId = sightId).execute()
        if (response.isSuccessful) {
            return response.body()!!//.sortedBy { dto -> dto.orderNumber }
                .map { dto -> MomentInfo(id = dto.id, name = dto.name, imagePath = ServerUtils.imageLink(dto.imagePath)) }
        } else {
            return null
        }
    }
}