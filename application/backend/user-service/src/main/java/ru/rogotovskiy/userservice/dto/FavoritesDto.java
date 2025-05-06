package ru.rogotovskiy.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Избранное пользователя")
public record FavoritesDto (
        @Schema(description = "Избранные туры")
        List<FavoriteTourDto> favoriteTours,
        @Schema(description = "Избранные достопримечательности")
        List<FavoriteSightDto> favoriteSights
) {
}
