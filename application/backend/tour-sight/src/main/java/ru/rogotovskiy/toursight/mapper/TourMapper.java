package ru.rogotovskiy.toursight.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rogotovskiy.toursight.dto.create.CreateTourDto;
import ru.rogotovskiy.toursight.dto.read.PreviewTourDto;
import ru.rogotovskiy.toursight.dto.read.TourDto;
import ru.rogotovskiy.toursight.entity.Tour;
import ru.rogotovskiy.toursight.entity.TourTranslation;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TourMapper {

    public TourDto toDto(Tour tour, TourTranslation translation) {
        return new TourDto(
                tour.getId(),
                translation.getName(),
                translation.getDescription(),
                translation.getCity(),
                tour.getDurationMinutes(),
                tour.getDistanceKm(),
                translation.getCategory(),
                translation.getType(),
                tour.getRating(),
                tour.getImagePath(),
                null
        );
    }

    public Tour toEntity(CreateTourDto dto) {
        return new Tour(
                null,
                null,
                null,
                BigDecimal.ZERO,
                null,
                null
        );
    }

    public PreviewTourDto toPreviewDto(Tour tour, TourTranslation translation) {
        return new PreviewTourDto(
                tour.getId(),
                translation.getName(),
                tour.getDistanceKm(),
                tour.getRating(),
                tour.getImagePath()
        );
    }
}
