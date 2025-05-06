package ru.rogotovskiy.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Данные избранного тура")
public record FavoriteTourDto(
        @Schema(description = "ID тура", example = "1")
        Integer id,
        @Schema(description = "Название тура", example = "Тур по главным достопримечательностям Воронежа")
        String name
) {
}
