package ru.rogotovskiy.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rogotovskiy.userservice.dto.favorites.FavoritesDto;
import ru.rogotovskiy.userservice.service.FavoritesService;

@RestController
@RequestMapping("/api/user/favorites")
@RequiredArgsConstructor
@Tag(name = "Избранное", description = "Добавление и удаление туров и достопримечательностей в избранное")
public class FavoritesController {

    private final FavoritesService favoritesService;

    @Operation(summary = "Получить список избранных туров и достопримечательностей пользователя")
    @ApiResponse(
            responseCode = "200",
            description = "Список избранного успешно получен",
            content = @Content(schema = @Schema(implementation = FavoritesDto.class))
    )
    @GetMapping
    public ResponseEntity<?> getFavorites(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(favoritesService.getAll(Integer.parseInt(userId)));
    }
}
