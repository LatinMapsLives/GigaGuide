package ru.rogotovskiy.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.auth.dto.LoginRequest;
import ru.rogotovskiy.auth.dto.RegistrationUserDTO;
import ru.rogotovskiy.auth.entity.User;
import ru.rogotovskiy.auth.exception.AccessDeniedException;
import ru.rogotovskiy.auth.exception.PasswordMismatchException;
import ru.rogotovskiy.auth.exception.UserAlreadyExistsException;
import ru.rogotovskiy.auth.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void register(RegistrationUserDTO registrationUserDTO) {
        if (userService.findByUsername(registrationUserDTO.username()).isPresent()) {
            throw new UserAlreadyExistsException(
                    "Пользователь с именем %s уже существует".formatted(registrationUserDTO.username())
            );
        }
        if (userService.findByEmail(registrationUserDTO.email()).isPresent()) {
            throw new UserAlreadyExistsException(
                    "Пользователь с почтой %s уже существует".formatted(registrationUserDTO.email())
            );
        }
        if (!registrationUserDTO.password().equals(registrationUserDTO.confirmPassword())) {
            throw new PasswordMismatchException("Пароли не совпадают");
        }
        userService.createUser(registrationUserDTO);
    }

    public String authorize(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.email(), loginRequest.password())
        );
        return jwtService.generateToken(userService.findByEmail(loginRequest.email()).orElseThrow(
                () -> new UserNotFoundException("Пользователь с почтой %s не найден".formatted(loginRequest.email()))
        ));
    }

    public String authorizeAdmin(LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.email()).orElseThrow(
                () -> new UserNotFoundException("Пользователь с почтой %s не найден".formatted(loginRequest.email())
        ));

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new AccessDeniedException("Отказано в доступе");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.email(), loginRequest.password())
        );
        return jwtService.generateToken(user);
    }
}
