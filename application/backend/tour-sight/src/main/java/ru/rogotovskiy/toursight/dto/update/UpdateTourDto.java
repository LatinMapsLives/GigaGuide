package ru.rogotovskiy.toursight.dto.update;

public record UpdateTourDto(
        Integer id,
        String name,
        String description,
        String city,
        String category,
        String type
) {
}
