package ru.rogotovskiy.reviews.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rogotovskiy.reviews.dto.CreateSightReviewDto;
import ru.rogotovskiy.reviews.service.SightReviewService;

@RestController
@RequestMapping("/api/reviews/sights")
@RequiredArgsConstructor
public class SightReviewController {

    private final SightReviewService service;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Integer sightId) {
        return ResponseEntity.ok(service.getAll(sightId));
    }

    @PostMapping
    public ResponseEntity<?> addReview(@RequestHeader("X-User-Id") String userId, @RequestParam Integer sightId,
                                       @RequestBody CreateSightReviewDto dto) {
        service.addReview(Integer.parseInt(userId), sightId, dto);
        return ResponseEntity.ok("Отзыв успешно добавлен");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteReview(@RequestHeader("X-User-Id") String userId, @RequestParam Integer reviewId) {
        service.deleteReview(Integer.parseInt(userId), reviewId);
        return ResponseEntity.ok("Отзыв успешно удалён");
    }
}
