package ru.rogotovskiy.toursight.dto.read;

import java.math.BigDecimal;

public record PreviewSightDto(
        Integer id,
        String name,
        BigDecimal latitude,
        BigDecimal longitude,
        BigDecimal rating,
        String imagePath
) {
}
