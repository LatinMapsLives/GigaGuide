package ru.rogotovskiy.toursight.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.rogotovskiy.toursight.dto.create.CreateMomentDto;
import ru.rogotovskiy.toursight.dto.read.MomentDto;
import ru.rogotovskiy.toursight.entity.Moment;

@Mapper(componentModel = "spring")
public interface MomentMapper {

    MomentDto toDto(Moment moment);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "orderNumber", target = "orderNumber")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    @Mapping(target = "sight", ignore = true)
    Moment toEntity(CreateMomentDto dto);
}
