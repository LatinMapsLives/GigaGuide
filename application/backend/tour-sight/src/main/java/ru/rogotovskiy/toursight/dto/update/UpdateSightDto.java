package ru.rogotovskiy.toursight.dto.update;

import java.math.BigDecimal;

public record UpdateSightDto(
        Integer id,
        String name,
        String description,
        String city,
        BigDecimal latitude,
        BigDecimal longitude
) {
}
