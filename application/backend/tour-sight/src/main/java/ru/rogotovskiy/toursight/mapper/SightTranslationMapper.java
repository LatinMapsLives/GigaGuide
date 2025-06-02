package ru.rogotovskiy.toursight.mapper;

import org.springframework.stereotype.Component;
import ru.rogotovskiy.toursight.dto.create.CreateSightDto;
import ru.rogotovskiy.toursight.entity.Language;
import ru.rogotovskiy.toursight.entity.SightTranslation;

@Component
public class SightTranslationMapper {

    public SightTranslation toEntity(CreateSightDto dto, Integer sightId, Language language) {
        return new SightTranslation(
                null,
                sightId,
                language,
                dto.name(),
                dto.description(),
                dto.city()
        );
    }
}
