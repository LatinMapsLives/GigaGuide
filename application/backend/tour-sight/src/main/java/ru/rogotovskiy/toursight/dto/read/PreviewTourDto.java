package ru.rogotovskiy.toursight.dto.read;

import java.math.BigDecimal;

public record PreviewTourDto(
        Integer id,
        String name,
        BigDecimal distanceKm,
        BigDecimal rating,
        BigDecimal latitude,
        BigDecimal longitude,
        String imagePath
) {
}
