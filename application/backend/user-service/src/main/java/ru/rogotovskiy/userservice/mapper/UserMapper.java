package ru.rogotovskiy.userservice.mapper;

import org.mapstruct.Mapper;
import ru.rogotovskiy.userservice.dto.user.UserDto;
import ru.rogotovskiy.userservice.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);
}
