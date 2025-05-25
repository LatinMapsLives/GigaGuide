package ru.rogotovskiy.toursight.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.rogotovskiy.toursight.dto.create.CreateSightDto;
import ru.rogotovskiy.toursight.dto.read.PreviewSightDto;
import ru.rogotovskiy.toursight.dto.read.SightDto;
import ru.rogotovskiy.toursight.entity.Sight;

@Mapper(componentModel = "spring")
public interface SightMapper {

    SightDto toDto(Sight sight);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "city", target = "city")
    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    Sight toEntity(CreateSightDto dto);

    PreviewSightDto toPreviewDto(Sight sight);
}
