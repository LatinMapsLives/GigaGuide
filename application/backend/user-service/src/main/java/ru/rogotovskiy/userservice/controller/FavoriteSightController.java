package ru.rogotovskiy.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rogotovskiy.userservice.service.FavoriteSightService;

import java.util.Locale;

@RestController
@RequestMapping("/api/user/favorites/sights")
@RequiredArgsConstructor
public class FavoriteSightController {

    private final FavoriteSightService favoriteSightService;
    private final MessageSource messageSource;

    @Operation(summary = "Добавить достопримечательность в избранное")
    @ApiResponse(responseCode = "200", description = "Достопримечательность успешно добавлена")
    @PostMapping
    public ResponseEntity<?> addSightToFavorites(@RequestHeader("X-User-Id") String userId, @RequestParam Integer sightId) {
        favoriteSightService.addSightToFavorites(Integer.parseInt(userId), sightId);
        return ResponseEntity.ok(messageSource.getMessage("user_service.success.favorites.add_sight", null, Locale.ROOT));
    }

    @Operation(summary = "Удалить достопримечательность из избранного")
    @ApiResponse(responseCode = "200", description = "Достопримечательность успешно удалена")
    @DeleteMapping
    public ResponseEntity<?> deleteSightFromFavorites(@RequestHeader("X-User-Id") String userId, @RequestParam Integer sightId) {
        favoriteSightService.deleteSightFromFavorites(Integer.parseInt(userId), sightId);
        return ResponseEntity.ok(messageSource.getMessage("user_service.success.favorites.delete_sight", null, Locale.ROOT));
    }
}
