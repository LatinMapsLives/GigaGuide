package ru.rogotovskiy.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rogotovskiy.userservice.dto.UpdateUserDto;
import ru.rogotovskiy.userservice.dto.UserDto;
import ru.rogotovskiy.userservice.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "Пользователь", description = "Управление данными пользователя")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Получить данные профиля пользователя")
    @ApiResponse(responseCode = "200", description = "Профиль успешно получен",
            content = @Content(schema = @Schema(implementation = UserDto.class)))
    @GetMapping
    public ResponseEntity<?> getCurrentUser(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(userService.getById(Integer.parseInt(userId)));
    }

    @Operation(summary = "Обновить профиль пользователя")
    @ApiResponse(responseCode = "200", description = "Профиль успешно обновлён")
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestHeader("X-User-Id") String userId,
                                        @RequestBody UpdateUserDto dto) {
        userService.updateUser(Integer.parseInt(userId), dto);
        return ResponseEntity.ok("Пользователь успешно обновлён");
    }

}
