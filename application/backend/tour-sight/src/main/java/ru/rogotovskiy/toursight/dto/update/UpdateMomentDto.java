package ru.rogotovskiy.toursight.dto.update;

import java.math.BigDecimal;

public record UpdateMomentDto (
        Integer id,
        String name,
        Integer orderNumber,
        String content,
        BigDecimal latitude,
        BigDecimal longitude
) {
}
