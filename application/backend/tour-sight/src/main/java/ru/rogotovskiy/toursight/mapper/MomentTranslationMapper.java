package ru.rogotovskiy.toursight.mapper;

import org.springframework.stereotype.Component;
import ru.rogotovskiy.toursight.dto.create.CreateMomentDto;
import ru.rogotovskiy.toursight.entity.Language;
import ru.rogotovskiy.toursight.entity.MomentTranslation;

@Component
public class MomentTranslationMapper {

    public MomentTranslation toEntity(CreateMomentDto dto, Integer momentId, Language language) {
        return new MomentTranslation(
                null,
                momentId,
                language,
                dto.name(),
                dto.content()
        );
    }
}
