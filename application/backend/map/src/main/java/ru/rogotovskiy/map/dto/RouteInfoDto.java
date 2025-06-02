package ru.rogotovskiy.map.dto;

import java.math.BigDecimal;

public record RouteInfoDto(
        BigDecimal distanceKm,
        Integer durationMinutes
) {
}
