package ru.rogotovskiy.toursight.dto.read;

import java.math.BigDecimal;

public record TourDto(
        Integer id,
        String name,
        String description,
        String city,
        Integer durationMinutes,
        BigDecimal distanceKm,
        String category,
        String type,
        BigDecimal rating,
        String imagePath
) {
}
