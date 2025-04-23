package ru.rogotovskiy.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rogotovskiy.userservice.service.FavoritesService;

@RestController
@RequestMapping("/api/user/favorites")
@RequiredArgsConstructor
public class FavoritesController {

    private final FavoritesService favoritesService;

    @GetMapping
    public ResponseEntity<?> getFavorites(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(favoritesService.getAll(Integer.parseInt(userId)));
    }

    @PostMapping("/tours")
    public ResponseEntity<?> addTourToFavorites(@RequestHeader("X-User-Id") String userId, @RequestParam Integer tourId) {
        favoritesService.addTourToFavorites(Integer.parseInt(userId), tourId);
        return ResponseEntity.ok("Тур успешно добавлен в избранное");
    }

    @PostMapping("/sights")
    public ResponseEntity<?> addSightToFavorites(@RequestHeader("X-User-Id") String userId, @RequestParam Integer sightId) {
        favoritesService.addSightToFavorites(Integer.parseInt(userId), sightId);
        return ResponseEntity.ok("Достопримечательность успешно добавлена в избранное");
    }

    @DeleteMapping("/tours")
    public ResponseEntity<?> deleteTourFromFavorites(@RequestHeader("X-User-Id") String userId, @RequestParam Integer tourId) {
        favoritesService.deleteTourFromFavorites(Integer.parseInt(userId), tourId);
        return ResponseEntity.ok("Тур успешно удалён из избранного");
    }

    @DeleteMapping("/sights")
    public ResponseEntity<?> deleteSightFromFavorites(@RequestHeader("X-User-Id") String userId, @RequestParam Integer sightId) {
        favoritesService.deleteSightFromFavorites(Integer.parseInt(userId), sightId);
        return ResponseEntity.ok("Достопримечательность успешно удалена из избранного");
    }
}
