package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.CoordinatesDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.MapPoint

class CoordinatesDTOtoMapPointMapper: Mapper<CoordinatesDTO, MapPoint> {
    override fun map(value: CoordinatesDTO): MapPoint {
        return MapPoint(value.latitude, value.longitude)
    }
}