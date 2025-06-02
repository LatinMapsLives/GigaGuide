package ru.rogotovskiy.toursight.mapper;

import org.springframework.stereotype.Component;
import ru.rogotovskiy.toursight.dto.create.CreateSightDto;
import ru.rogotovskiy.toursight.dto.read.PreviewSightDto;
import ru.rogotovskiy.toursight.dto.read.SightDto;
import ru.rogotovskiy.toursight.entity.Sight;
import ru.rogotovskiy.toursight.entity.SightTranslation;

import java.math.BigDecimal;

@Component
public class SightMapper {

    public SightDto toDto(Sight sight, SightTranslation translation) {
        return new SightDto(
                sight.getId(),
                translation.getName(),
                translation.getDescription(),
                translation.getCity(),
                sight.getImagePath(),
                sight.getLatitude(),
                sight.getLongitude(),
                sight.getRating());
    }

    public PreviewSightDto toPreviewDto(Sight sight, SightTranslation translation) {
        return new PreviewSightDto(
                sight.getId(),
                translation.getName(),
                sight.getLatitude(),
                sight.getLongitude(),
                sight.getRating(),
                sight.getImagePath()
        );
    }

    public Sight toEntity(CreateSightDto dto) {
        return new Sight(
                null,
                null,
                dto.latitude(),
                dto.longitude(),
                BigDecimal.ZERO,
                null
        );
    }
}
