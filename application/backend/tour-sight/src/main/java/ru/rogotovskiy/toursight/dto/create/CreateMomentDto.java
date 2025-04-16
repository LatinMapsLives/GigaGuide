package ru.rogotovskiy.toursight.dto.create;

import java.math.BigDecimal;

public record CreateMomentDto(
        String name,
        Integer orderNumber,
        String content,
        Integer sightId,
        BigDecimal latitude,
        BigDecimal longitude
) {
}
