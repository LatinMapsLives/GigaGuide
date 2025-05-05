package ru.rogotovskiy.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rogotovskiy.auth.dto.JwtResponse;
import ru.rogotovskiy.auth.dto.LoginRequest;
import ru.rogotovskiy.auth.dto.RegistrationUserDTO;
import ru.rogotovskiy.auth.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationUserDTO registrationUserDTO) {
        authService.register(registrationUserDTO);
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Пользователь успешно создан");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new JwtResponse(
                "Вы успешно авторизовались",
                authService.authorize(loginRequest)
        ));
    }

}
