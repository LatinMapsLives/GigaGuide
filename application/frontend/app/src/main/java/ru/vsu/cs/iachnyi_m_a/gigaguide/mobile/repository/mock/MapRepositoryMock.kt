package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.mock

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.MapPoint
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.MomentOnMap
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight.Sight
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.MapRepository

class MapRepositoryMock : MapRepository {

    private var sights = listOf<Sight>(
        Sight(
            id = 1,
            name = "Парк \"Орлёнок\"",
            description = "Парк Орлёнок — парк культуры и отдыха в г. Воронеж. Парк располагается в Центральном районе города на территории, ограниченной улицами Студенческой, Феоктистова, Чайковского, Фридриха Энгельса недалеко от железнодорожного вокзала. Парк работает только в тёплое время года — с мая до первых заморозков.",
            momentNames = listOf(
                "Мальчик на коне",
                "Памятник Троепольскому",
                "Фонтан",
                "Некрополь",
                "Памятник Мандельштаму"
            ),
            time = 20,
            latitude = 51.674976,
            longitude = 39.206196
        ),
        Sight(
            id = 0,
            name = "Первомайский сад",
            description = "Первомайский сад — любимый всеми воронежцами исторический парк в центре города, который был восстановлен после Великой Отечественной войны. Здесь можно увидеть солнечные часы, фонтан и памятник Петру I. Главное украшение парка - огромный храм, Благовещенский кафедральный собор, построенный с 1998 по 2009 год",
            momentNames = listOf(
                "Солнечные часы",
                "Фонтан",
                "Благовещенский собор",
                "Памятник Петру I"
            ),
            time = 30,
            latitude = 51.675388,
            longitude = 39.211002
        )
    )

    private var moments = listOf(
        MomentOnMap(
            1,
            "Солнечные часы",
            51.67393,
            39.211287,
            "https://drive.google.com/uc?export=download&id=1TGLfYaxjLhAfPUcYAFWIAdGtpd2C0N0L",
            ""
        ),
        MomentOnMap(
            2,
            "Пушки",
            51.673811,
            39.211592,
            "https://drive.google.com/uc?export=download&id=1LecUqI3_67PO6F2jppHWtfFbKlWHz4Ar",
            ""
        ),
        MomentOnMap(
            3,
            "Памятник Петру I",
            51.673825,
            39.211688,
            "https://drive.google.com/uc?export=download&id=1TGLfYaxjLhAfPUcYAFWIAdGtpd2C0N0L",
            ""
        ),
        MomentOnMap(
            4,
            "Фонтан",
            51.67373,
            39.212067,
            "https://drive.google.com/uc?export=download&id=1LecUqI3_67PO6F2jppHWtfFbKlWHz4Ar",
            ""
        )
    )

    override suspend fun getCoordinatedOfSight(sightId: Long): MapPoint {
        return sights.first { s -> s.id == sightId }.let { MapPoint(it.latitude, it.longitude) }
    }

    override suspend fun getCoordinatesOfMoment(sightId: Long): MapPoint {
        return moments.first { s -> s.id == sightId }.let { MapPoint(it.latitude, it.longitude) }
    }

    override suspend fun getRouteOfSight(sightId: Long): List<MapPoint>? {
        return listOf(
            MapPoint(51.67393, 39.211287),
            MapPoint(51.673846, 39.211615),
            MapPoint(51.673811, 39.211592),
            MapPoint(51.673825, 39.211688),
        )
    }
}