package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.PreviewSightDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightTourThumbnail

class PreviewSightDTOMapper: Mapper<PreviewSightDTO, SightTourThumbnail> {
    override fun map(value: PreviewSightDTO): SightTourThumbnail {
        return SightTourThumbnail(sightId = value.id.toLong(), imageLink = "", rating = value.rating, name = value.name, proximity = 0f)
    }

}