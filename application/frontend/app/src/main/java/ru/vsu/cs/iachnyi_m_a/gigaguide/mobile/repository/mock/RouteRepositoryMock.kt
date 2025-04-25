package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.mock

import kotlinx.coroutines.delay
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.MapPoint
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.Moment
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.SightRoute
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.RouteRepository

class RouteRepositoryMock : RouteRepository {
    override suspend fun getSightRoute(sightId: Long): SightRoute {
        delay(1000)
        return SightRoute(
            routePoints = listOf(
                MapPoint(51.67393, 39.211287),
                MapPoint(51.673846, 39.211615),
                MapPoint(51.673811, 39.211592),
                MapPoint(51.673825, 39.211688),
                MapPoint(51.67373, 39.212067)
            ), moments = listOf(
                Moment("Солнечные часы", 51.67393, 39.211287),
                Moment("Пушки", 51.673811, 39.211592),
                Moment("Памятник Петру I", 51.673825, 39.211688),
                Moment("Фонтан", 51.67373, 39.212067)
            )
        )
    }
}