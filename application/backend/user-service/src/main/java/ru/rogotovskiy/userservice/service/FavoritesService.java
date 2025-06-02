package ru.rogotovskiy.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.userservice.dto.favorites.FavoritesDto;

@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final FavoriteSightService favoriteSightService;
    private final FavoriteTourService favoriteTourService;

    public FavoritesDto getAll(Integer userId, String language) {
        return new FavoritesDto(
                favoriteTourService.getAll(userId, language),
                favoriteSightService.getAll(userId, language)
        );
    }

}
