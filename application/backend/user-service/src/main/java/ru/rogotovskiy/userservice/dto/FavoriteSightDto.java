package ru.rogotovskiy.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Данные избранной достопримечательности")
public record FavoriteSightDto(
        @Schema(description = "ID достопримечательности", example = "1")
        Integer id,
        @Schema(description = "Название достопримечательности", example = "Парк Орлёнок")
        String name
) {
}
