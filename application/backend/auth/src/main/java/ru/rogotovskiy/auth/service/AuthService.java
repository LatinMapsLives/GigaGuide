package ru.rogotovskiy.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.auth.dto.RegistrationUserDto;
import ru.rogotovskiy.auth.exception.PasswordMismatchException;
import ru.rogotovskiy.auth.exception.UserAlreadyExistsException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    public void register(RegistrationUserDto registrationUserDto) {
        if (userService.findByUsername(registrationUserDto.username()).isPresent()) {
            throw new UserAlreadyExistsException(
                    "Пользователь с именем %s уже существует".formatted(registrationUserDto.username())
            );
        }
        if (userService.findByEmail(registrationUserDto.email()).isPresent()) {
            throw new UserAlreadyExistsException(
                    "Пользователь с почтой %s уже существует".formatted(registrationUserDto.email())
            );
        }
        if (!registrationUserDto.password().equals(registrationUserDto.confirmPassword())) {
            throw new PasswordMismatchException("Пароли не совпадают");
        }
        userService.createUser(registrationUserDto);
    }
}
