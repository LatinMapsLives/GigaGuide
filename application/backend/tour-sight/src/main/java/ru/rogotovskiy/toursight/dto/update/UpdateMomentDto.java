package ru.rogotovskiy.toursight.dto.update;

import java.math.BigDecimal;

public record UpdateMomentDto (
        String name,
        Integer orderNumber,
        String content,
        BigDecimal latitude,
        BigDecimal longitude
) {
}
