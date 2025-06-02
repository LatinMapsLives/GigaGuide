package ru.rogotovskiy.map.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rogotovskiy.map.dto.RouteInfoDto;
import ru.rogotovskiy.map.entity.Sight;
import ru.rogotovskiy.map.enums.RouteType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {

    @Value("${graphhopper.api.key}")
    private String apiKey;

    private static final String GRAPHHOPPER_URL = "https://graphhopper.com/api/1/route";

    public RouteInfoDto calculateRouteInfo(List<Sight> sights, String routeTypeString) {
        if (sights.size() < 2) {
            return new RouteInfoDto(BigDecimal.ZERO, 0);
        }

        RouteType routeType = RouteType.fromString(routeTypeString);
        String profile = routeType.getGraphHopperProfile(); // Например: "car", "foot", "bike"

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(GRAPHHOPPER_URL)
                .queryParam("profile", profile)
                .queryParam("locale", "ru")
                .queryParam("calc_points", "false")
                .queryParam("key", apiKey);

        for (Sight sight : sights) {
            builder.queryParam("point", sight.getLatitude() + "," + sight.getLongitude());
        }

        String url = builder.toUriString();
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);

            JsonNode path = response.getBody().path("paths").get(0);
            double distanceMeters = path.path("distance").asDouble();
            double durationMillis = path.path("time").asDouble();

            BigDecimal distanceKm = BigDecimal.valueOf(distanceMeters / 1000.0).setScale(2, RoundingMode.HALF_UP);
            int durationMinutes = (int) Math.round(durationMillis / 60000.0); // миллисекунды → минуты

            return new RouteInfoDto(distanceKm, durationMinutes);

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при расчёте маршрута через GraphHopper", e);
        }
    }
}
