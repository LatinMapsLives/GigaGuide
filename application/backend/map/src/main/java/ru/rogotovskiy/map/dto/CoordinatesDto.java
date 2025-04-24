package ru.rogotovskiy.map.dto;

import java.math.BigDecimal;

public record CoordinatesDto(
        Integer id,
        BigDecimal latitude,
        BigDecimal longitude
) {
}
