package ru.rogotovskiy.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.rogotovskiy.userservice.dto.UpdateUserDto;
import ru.rogotovskiy.userservice.jwt.JwtAuthentication;
import ru.rogotovskiy.userservice.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getCurrentUser(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(userService.getById(Integer.parseInt(userId)));
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestHeader("X-User-Id") String userId,
                                        @RequestBody UpdateUserDto dto) {
        userService.updateUser(Integer.parseInt(userId), dto);
        return ResponseEntity.ok("Пользователь успешно обновлён");
    }

}
