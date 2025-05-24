package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.TourDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.TourInfo

class TourDTOMapper: Mapper<TourDTO, TourInfo>{
    override fun map(value: TourDTO): TourInfo {
        return TourInfo(
            id = value.id.toLong(),
            name = value.name,
            description = value.description,
            durationMinutes = value.durationMinutes,
            distanceKm = value.distanceKm,
            category = value.category,
            type = value.type,
            rating = value.rating,
            imageLink = ServerUtils.imageLink(value.imagePath),
        )
    }

}
