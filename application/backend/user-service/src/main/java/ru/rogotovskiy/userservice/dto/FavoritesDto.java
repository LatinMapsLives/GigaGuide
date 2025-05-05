package ru.rogotovskiy.userservice.dto;

import java.util.List;

public record FavoritesDto (
        List<FavoriteTourDto> favoriteTours,
        List<FavoriteSightDto> favoriteSights
) {
}
