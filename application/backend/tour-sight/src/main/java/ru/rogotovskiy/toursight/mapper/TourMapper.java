package ru.rogotovskiy.toursight.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.rogotovskiy.toursight.dto.create.CreateTourDto;
import ru.rogotovskiy.toursight.dto.read.TourDto;
import ru.rogotovskiy.toursight.entity.Tour;

@Mapper(componentModel = "spring")
public interface TourMapper {

    TourDto toDto(Tour tour);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "city", target = "city")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "type", target = "type")
    @Mapping(target = "sights", ignore = true)
    Tour toEntity(CreateTourDto dto);
}
