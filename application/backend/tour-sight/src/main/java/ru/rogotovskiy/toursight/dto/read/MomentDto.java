package ru.rogotovskiy.toursight.dto.read;

public record MomentDto(
        Integer id,
        String name,
        Integer orderNumber,
        String imagePath
) {
}
