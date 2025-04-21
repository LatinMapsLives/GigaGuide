package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository

import kotlinx.coroutines.delay
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.Sight
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightOnMapInfo
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightPageInfo

class SightRepository {

    private var sights = listOf<Sight>(
        Sight(
            id = 1,
            name = "Парк \"Орлёнок\"",
            description = "Парк Орлёнок — парк культуры и отдыха в г. Воронеж. Парк располагается в Центральном районе города на территории, ограниченной улицами Студенческой, Феоктистова, Чайковского, Фридриха Энгельса недалеко от железнодорожного вокзала. Парк работает только в тёплое время года — с мая до первых заморозков.",
            momentNames = listOf("Мальчик на коне", "Памятник Троепольскому", "Фонтан", "Некрополь", "Памятник Мандельштаму"),
            time = 20,
            latitude = 51.674976,
            longitude = 39.206196
        ),
        Sight(
            id = 0,
            name = "Первомайский сад",
            description = "Первомайский сад — любимый всеми воронежцами исторический парк в центре города, который был восстановлен после Великой Отечественной войны. Здесь можно увидеть солнечные часы, фонтан и памятник Петру I. Главное украшение парка - огромный храм, Благовещенский кафедральный собор, построенный с 1998 по 2009 год",
            momentNames = listOf("Солнечные часы", "Фонтан", "Благовещенский собор", "Памятник Петру I"),
            time = 30,
            latitude = 51.675388,
            longitude = 39.211002
        )
    )

    suspend fun getSightPageInfoById(id: Long): SightPageInfo {
        delay(1000)
        return SightPageInfo(sights.find { sight -> sight.id == id }!!)
    }

    suspend fun getAllSightOnMapInfos(): List<SightOnMapInfo> {
        delay(1000)
        return sights.map { s -> SightOnMapInfo(s) }
    }
}