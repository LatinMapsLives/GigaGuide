package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile

import org.junit.Test
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.CoordinatesDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper.CoordinatesDTOMapper
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.MapPoint

class CoordinateDTOtoMapPointMapperTest {

    @Test
    fun mapTest() {
        var result = CoordinatesDTOMapper().map(
            CoordinatesDTO(
                id = 0,
                latitude = 23.5555,
                longitude = 69.312312
            )
        )
        assert(MapPoint(latitude = 23.5555, longitude = 69.312312) == result)
    }
}