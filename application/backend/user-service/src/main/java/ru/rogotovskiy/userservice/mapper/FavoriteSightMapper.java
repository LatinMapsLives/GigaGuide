package ru.rogotovskiy.userservice.mapper;

import org.mapstruct.Mapper;
import ru.rogotovskiy.userservice.dto.favorites.FavoriteSightDto;
import ru.rogotovskiy.userservice.entity.Sight;

@Mapper(componentModel = "spring")
public interface FavoriteSightMapper {

    FavoriteSightDto toDto(Sight sight);
}
