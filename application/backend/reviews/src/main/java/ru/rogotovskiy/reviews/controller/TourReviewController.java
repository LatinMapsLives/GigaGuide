package ru.rogotovskiy.reviews.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rogotovskiy.reviews.dto.CreateTourReviewDto;
import ru.rogotovskiy.reviews.service.TourReviewService;

@RestController
@RequestMapping("/api/reviews/tours")
@RequiredArgsConstructor
public class TourReviewController {

    private final TourReviewService tourReviewService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Integer tourId) {
        return ResponseEntity.ok(tourReviewService.getAll(tourId));
    }

    @PostMapping
    public ResponseEntity<?> addReview(@RequestHeader("X-User-Id") String userId, @RequestParam Integer tourId,
                                       @RequestBody CreateTourReviewDto dto) {
        tourReviewService.addReview(Integer.parseInt(userId), tourId, dto);
        return ResponseEntity.ok("Отзыв добавлен успешно");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteReview(@RequestHeader("X-User-Id") String userId, @RequestParam Integer reviewId) {
        tourReviewService.deleteReview(Integer.parseInt(userId), reviewId);
        return ResponseEntity.ok("Отзыв успешно удалён");
    }
}
