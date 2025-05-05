package ru.rogotovskiy.toursight.dto.update;

import java.math.BigDecimal;

public record UpdateSightDto(
        String name,
        String description,
        String city,
        BigDecimal latitude,
        BigDecimal longitude
) {
}
