package ru.rogotovskiy.userservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rogotovskiy.userservice.dto.favorites.FavoriteTourDto;
import ru.rogotovskiy.userservice.entity.Tour;
import ru.rogotovskiy.userservice.service.TourTranslationService;

@Component
@RequiredArgsConstructor
public class FavoriteTourMapper {

    private final TourTranslationService tourTranslationService;

    public FavoriteTourDto toDto(Tour tour, String language) {
        return new FavoriteTourDto(
                tour.getId(),
                tourTranslationService.getTourTranslation(tour.getId(), language).getName()
        );
    }
}
