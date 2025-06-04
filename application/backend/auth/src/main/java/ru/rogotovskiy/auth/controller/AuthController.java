package ru.rogotovskiy.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rogotovskiy.auth.dto.ErrorResponse;
import ru.rogotovskiy.auth.dto.JwtResponse;
import ru.rogotovskiy.auth.dto.LoginRequest;
import ru.rogotovskiy.auth.dto.RegistrationUserDTO;
import ru.rogotovskiy.auth.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация", description = "Регистрация и авторизация пользователей")
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Создаёт нового пользователя на основе переданных данных"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь успешно зарегистрирован",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(example = "Пользователь успешно создан")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные данные или пользователь уже существует",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера"
            )
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationUserDTO registrationUserDTO) {
        authService.register(registrationUserDTO);
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Пользователь успешно создан");
    }

    @Operation(
            summary = "Авторизация пользователя",
            description = "Авторизует пользователя и возвращает JWT токен"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная авторизация",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Неверные учётные данные",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера"
            )
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new JwtResponse(
                "Вы успешно авторизовались",
                authService.authorize(loginRequest)
        ));
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new JwtResponse(
                "Вы успешно авторизовались",
                authService.authorizeAdmin(loginRequest)
        ));
    }

}
