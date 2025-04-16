package ru.rogotovskiy.toursight.dto.create;

import java.math.BigDecimal;

public record CreateSightDto(
        String name,
        String description,
        String city,
        BigDecimal latitude,
        BigDecimal longitude
) {
}
