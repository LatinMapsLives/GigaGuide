package ru.rogotovskiy.toursight.dto.read;

public record SightDto(
        Integer id,
        String name,
        String description,
        String city,
        String imagePath
) {
}
