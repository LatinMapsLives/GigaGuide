package ru.rogotovskiy.map.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record PointDto(
        @Schema(description = "Широта", example = "51.657333")
        BigDecimal latitude,
        @Schema(description = "Долгота", example = "39.216445")
        BigDecimal longitude
) {
}
