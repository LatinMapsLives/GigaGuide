package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.GlobalConstants
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.SightDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightInfo

class SightDTOtoSightInfoMapper : Mapper<SightDTO, SightInfo> {
    override fun map(value: SightDTO): SightInfo {
        return SightInfo(
            id = value.id,
            name = value.name,
            description = value.description,
            time = 30,
            imageLink = "${GlobalConstants.SERVER_ADDRESS}/api/tour-sight/image?fileName=${value.imagePath}"
        )
    }
}
