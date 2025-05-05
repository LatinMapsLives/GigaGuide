package ru.rogotovskiy.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.auth.dto.LoginRequest;
import ru.rogotovskiy.auth.dto.RegistrationUserDto;
import ru.rogotovskiy.auth.exception.PasswordMismatchException;
import ru.rogotovskiy.auth.exception.UserAlreadyExistsException;
import ru.rogotovskiy.auth.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

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

    public String authorize(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.email(), loginRequest.password())
        );
        return jwtService.generateToken(userService.findByEmail(loginRequest.email()).orElseThrow(
                () -> new UserNotFoundException("Пользователь с почтой %s не найден".formatted(loginRequest.email()))
        ));
    }
}
