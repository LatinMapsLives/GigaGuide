package ru.rogotovskiy.userservice.mapper;

import org.mapstruct.Mapper;
import ru.rogotovskiy.userservice.dto.FavoriteTourDto;
import ru.rogotovskiy.userservice.entity.Tour;

@Mapper(componentModel = "spring")
public interface FavoriteTourMapper {

    public FavoriteTourDto toDto(Tour tour);
}
