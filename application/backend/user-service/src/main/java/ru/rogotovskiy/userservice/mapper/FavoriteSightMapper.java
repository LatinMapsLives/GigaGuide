package ru.rogotovskiy.userservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rogotovskiy.userservice.dto.favorites.FavoriteSightDto;
import ru.rogotovskiy.userservice.entity.Sight;
import ru.rogotovskiy.userservice.service.SightTranslationService;

@Component
@RequiredArgsConstructor
public class FavoriteSightMapper {

    private final SightTranslationService sightTranslationService;

    public FavoriteSightDto toDto(Sight sight, String languageCode) {
        return new FavoriteSightDto(
                sight.getId(),
                sightTranslationService.getSighTranslation(sight.getId(), languageCode).getName()
        );
    }
}
