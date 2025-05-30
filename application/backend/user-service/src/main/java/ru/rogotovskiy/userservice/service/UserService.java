package ru.rogotovskiy.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.userservice.dto.user.UpdateUserDto;
import ru.rogotovskiy.userservice.dto.user.UserDto;
import ru.rogotovskiy.userservice.entity.User;
import ru.rogotovskiy.userservice.exception.EmailAlreadyExistsException;
import ru.rogotovskiy.userservice.exception.InvalidPasswordException;
import ru.rogotovskiy.userservice.exception.UserNotFoundException;
import ru.rogotovskiy.userservice.mapper.UserMapper;
import ru.rogotovskiy.userservice.repository.UserRepository;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MessageSource messageSource;

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(
                        "USER_NOT_FOUND",
                        messageSource.getMessage("user_service.errors.user.not_found", null, Locale.ROOT)
                )
        );
    }

    public void updateUser(Integer id, UpdateUserDto dto) {
        User user = getUserById(id);

        if (dto.username() != null && !dto.username().isEmpty()) {
            user.setUsername(dto.username());
        }

        if (dto.email() != null && !dto.email().isEmpty()) {
            if (userRepository.existsByEmail(dto.email())) {
                throw new EmailAlreadyExistsException(
                        "EMAIL_EXISTS",
                        messageSource.getMessage("user_service.errors.email.exists", null, Locale.ROOT)
                );
            }
            user.setEmail(dto.email());
        }

        if (dto.oldPassword() != null && dto.newPassword() != null && !dto.newPassword().isEmpty()) {

            if (!bCryptPasswordEncoder.matches(dto.oldPassword(), user.getPassword())) {
                throw new InvalidPasswordException(
                        "INVALID_PASSWORD",
                        messageSource.getMessage("user_service.errors.password.invalid", null, Locale.ROOT)
                );
            }

            user.setPassword(bCryptPasswordEncoder.encode(dto.newPassword()));
        }
        userRepository.save(user);

    }

    public UserDto getById(Integer userId) {
        return userMapper.toDto(getUserById(userId));
    }
}
