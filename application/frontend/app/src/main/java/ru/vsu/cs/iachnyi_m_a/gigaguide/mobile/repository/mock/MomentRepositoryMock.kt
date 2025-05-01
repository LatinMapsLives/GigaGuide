package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.mock

import kotlinx.coroutines.delay
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.MomentOnMap
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.moment.MomentInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.MomentRepository

class MomentRepositoryMock: MomentRepository{

    private val momentOnMaps = listOf(
        MomentOnMap(1,"Солнечные часы", 51.67393, 39.211287, "https://drive.google.com/uc?export=download&id=1TGLfYaxjLhAfPUcYAFWIAdGtpd2C0N0L"),
        MomentOnMap(2,"Пушки", 51.673811, 39.211592, "https://drive.google.com/uc?export=download&id=1LecUqI3_67PO6F2jppHWtfFbKlWHz4Ar"),
        MomentOnMap(3,"Памятник Петру I", 51.673825, 39.211688, "https://drive.google.com/uc?export=download&id=1TGLfYaxjLhAfPUcYAFWIAdGtpd2C0N0L"),
        MomentOnMap(4,"Фонтан", 51.67373, 39.212067, "https://drive.google.com/uc?export=download&id=1LecUqI3_67PO6F2jppHWtfFbKlWHz4Ar")
    )

    override suspend fun getSightMoments(sightId: Long): List<MomentInfo>? {
        delay(1000)
        return momentOnMaps.map { m -> MomentInfo(id = m.id, name = m.name) }
    }
}