package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.ServerUtils
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.MomentDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.moment.MomentInfo

class MomentDTOtoMomentInfoMapper: Mapper<MomentDTO, MomentInfo> {
    override fun map(value: MomentDTO): MomentInfo {
        return MomentInfo(id = value.id, name = value.name, imagePath = ServerUtils.imageLink(value.imagePath))
    }
}