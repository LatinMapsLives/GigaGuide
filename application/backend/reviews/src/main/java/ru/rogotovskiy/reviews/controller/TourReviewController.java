package ru.rogotovskiy.reviews.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rogotovskiy.reviews.dto.CreateTourReviewDto;
import ru.rogotovskiy.reviews.dto.SightReviewDto;
import ru.rogotovskiy.reviews.dto.TourReviewDto;
import ru.rogotovskiy.reviews.service.TourReviewService;

import java.security.Principal;

@RestController
@RequestMapping("/api/reviews/tours")
@RequiredArgsConstructor
public class TourReviewController {

    private final TourReviewService tourReviewService;

    @Operation(
            summary = "Получить все отзывы о туре",
            description = "Возвращает список всех отзывов о туре"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список отзывов успешно получен",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TourReviewDto.class)))
    )
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Integer tourId) {
        return ResponseEntity.ok(tourReviewService.getAll(tourId));
    }

    @Operation(
            summary = "Добавить отзыв о туре",
            description = "Добавляет отзыв от пользователя о туре"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Отзыв успешно добавлен"
    )
    @PostMapping
    public ResponseEntity<?> addReview(@RequestHeader("X-User-Id") String userId, @RequestParam Integer tourId,
                                       @RequestBody CreateTourReviewDto dto) {
        tourReviewService.addReview(Integer.parseInt(userId), tourId, dto);
        return ResponseEntity.ok("Отзыв добавлен успешно");
    }

    @Operation(
            summary = "Удалить отзыв о туре",
            description = "Удаляет отзыв, принадлежащий пользователю"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Отзыв успешно удалён"
    )
    @DeleteMapping
    public ResponseEntity<?> deleteReview(@RequestHeader("X-User-Id") String userId, @RequestParam Integer reviewId) {
        tourReviewService.deleteReview(Integer.parseInt(userId), reviewId);
        return ResponseEntity.ok("Отзыв успешно удалён");
    }
}
