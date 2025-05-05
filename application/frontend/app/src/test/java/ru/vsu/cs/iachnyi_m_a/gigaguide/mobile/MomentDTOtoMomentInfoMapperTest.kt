package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile

import org.junit.Test
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.MomentDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper.MomentDTOtoMomentInfoMapper
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.MapPoint
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.moment.MomentInfo

class MomentDTOtoMomentInfoMapperTest {
    @Test
    fun mapTest(){
        var result = MomentDTOtoMomentInfoMapper().map(
            MomentDTO(
                name = "moment1",
                id = 1,
                orderNumber = 1,
                imagePath = "f7f830-jdfakj-hkadas.jpg"
            )
        )
        assert(MomentInfo(id=1, name = "moment1", imagePath = "${GlobalConstants.SERVER_ADDRESS}/api/tour-sight/image?fileName=f7f830-jdfakj-hkadas.jpg") == result)
    }
}