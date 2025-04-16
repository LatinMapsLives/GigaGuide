package ru.rogotovskiy.toursight.dto.update;

public record UpdateTourDto(
        String name,
        String description,
        String city,
        String category,
        String type
) {
}
