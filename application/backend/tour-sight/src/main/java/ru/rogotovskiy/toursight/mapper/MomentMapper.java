package ru.rogotovskiy.toursight.mapper;

import org.springframework.stereotype.Component;
import ru.rogotovskiy.toursight.dto.create.CreateMomentDto;
import ru.rogotovskiy.toursight.dto.read.MomentDto;
import ru.rogotovskiy.toursight.entity.Moment;
import ru.rogotovskiy.toursight.entity.MomentTranslation;

@Component
public class MomentMapper {

    public MomentDto toDto(Moment moment, MomentTranslation momentTranslation) {
        return new MomentDto(
                moment.getId(),
                momentTranslation.getName(),
                moment.getOrderNumber(),
                moment.getImagePath(),
                momentTranslation.getContent()
        );
    }

    public Moment toEntity(CreateMomentDto dto) {
        return new Moment(
                null,
                dto.orderNumber(),
                null,
                dto.latitude(),
                dto.longitude(),
                null
        );
    }
}
