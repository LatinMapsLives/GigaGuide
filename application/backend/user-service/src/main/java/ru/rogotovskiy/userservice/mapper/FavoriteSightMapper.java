package ru.rogotovskiy.userservice.mapper;

import org.mapstruct.Mapper;
import ru.rogotovskiy.userservice.dto.FavoriteSightDto;
import ru.rogotovskiy.userservice.entity.Sight;

@Mapper(componentModel = "spring")
public interface FavoriteSightMapper {

    public FavoriteSightDto toDto(Sight sight);
}
