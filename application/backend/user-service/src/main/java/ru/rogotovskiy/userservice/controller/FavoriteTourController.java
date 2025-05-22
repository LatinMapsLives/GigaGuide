package ru.rogotovskiy.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rogotovskiy.userservice.service.FavoriteTourService;

@RestController
@RequestMapping("/api/user/favorites/tours")
@RequiredArgsConstructor
public class FavoriteTourController {

    private final FavoriteTourService favoriteTourService;

    @Operation(summary = "Добавить тур в избранное")
    @ApiResponse(responseCode = "200", description = "Тур успешно добавлен")
    @PostMapping
    public ResponseEntity<?> addTourToFavorites(@RequestHeader("X-User-Id") String userId, @RequestParam Integer tourId) {
        favoriteTourService.addTourToFavorites(Integer.parseInt(userId), tourId);
        return ResponseEntity.ok("Тур успешно добавлен в избранное");
    }

    @Operation(summary = "Удалить тур из избранного")
    @ApiResponse(responseCode = "200", description = "Тур успешно удалён")
    @DeleteMapping
    public ResponseEntity<?> deleteTourFromFavorites(@RequestHeader("X-User-Id") String userId, @RequestParam Integer tourId) {
        favoriteTourService.deleteTourFromFavorites(Integer.parseInt(userId), tourId);
        return ResponseEntity.ok("Тур успешно удалён из избранного");
    }
}
