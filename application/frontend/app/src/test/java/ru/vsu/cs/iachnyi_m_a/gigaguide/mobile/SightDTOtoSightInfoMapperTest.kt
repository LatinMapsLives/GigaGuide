package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile

import org.junit.Test
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.SightDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper.SightDTOMapper
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.SightInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ServerUtils

class SightDTOtoSightInfoMapperTest {
    @Test
    fun mapTest() {
        var result = SightDTOMapper().map(
            SightDTO(
                1,
                "sight1",
                "desc",
                "Воронеж",
                "nbdkas-41289312-das.jpg"
            )
        )
        assert(
            result == SightInfo(
                id = 1,
                description = "desc",
                name = "sight1",
                time = 30,

                imageLink = "${ServerUtils.SERVER_ADDRESS}/api/tour-sight/image?fileName=nbdkas-41289312-das.jpg"

            )
        )
    }
}