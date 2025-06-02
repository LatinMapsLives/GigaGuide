package ru.rogotovskiy.toursight.mapper;

import org.springframework.stereotype.Component;
import ru.rogotovskiy.toursight.dto.create.CreateTourDto;
import ru.rogotovskiy.toursight.entity.Language;
import ru.rogotovskiy.toursight.entity.TourTranslation;

@Component
public class TourTranslationMapper {

    public TourTranslation toEntity(CreateTourDto dto, Integer tourId, Language language) {
        return new TourTranslation(
                null,
                tourId,
                language,
                dto.getName(),
                dto.getDescription(),
                dto.getCity(),
                dto.getCategory(),
                dto.getType()
        );
    }
}
