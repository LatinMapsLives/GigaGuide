package ru.rogotovskiy.reviews.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rogotovskiy.reviews.dto.create.CreateTourReviewDto;
import ru.rogotovskiy.reviews.dto.read.TourReviewDto;
import ru.rogotovskiy.reviews.service.TourReviewService;

import java.util.Locale;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class TourReviewController {

    private final TourReviewService tourReviewService;
    private final MessageSource messageSource;

    @Operation(
            summary = "Получить все отзывы о туре",
            description = "Возвращает список всех отзывов о туре"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список отзывов успешно получен",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TourReviewDto.class)))
    )
    @GetMapping("/tours")
    public ResponseEntity<?> getAll(@RequestParam Integer tourId, @RequestHeader(value = "X-User-Id", required = false) String userId) {
        return ResponseEntity.ok(tourReviewService.getAll(tourId, userId));
    }

    @Operation(
            summary = "Добавить отзыв о туре",
            description = "Добавляет отзыв от пользователя о туре"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Отзыв успешно добавлен"
    )
    @PostMapping("/tours")
    public ResponseEntity<?> addReview(@RequestHeader("X-User-Id") String userId, @RequestParam Integer tourId,
                                       @RequestBody CreateTourReviewDto dto) {
        tourReviewService.addReview(Integer.parseInt(userId), tourId, dto);
        return ResponseEntity.ok(messageSource.getMessage("reviews.success.add_review", null, Locale.ROOT));
    }

    @Operation(
            summary = "Удалить отзыв о туре",
            description = "Удаляет отзыв, принадлежащий пользователю"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Отзыв успешно удалён"
    )
    @DeleteMapping("/tours")
    public ResponseEntity<?> deleteReview(@RequestHeader("X-User-Id") String userId, @RequestParam Integer reviewId) {
        tourReviewService.deleteReview(Integer.parseInt(userId), reviewId);
        return ResponseEntity.ok(messageSource.getMessage("reviews.success.delete_review", null, Locale.ROOT));
    }

    @DeleteMapping("/admin/tours")
    public ResponseEntity<?> adminDeleteReview(@RequestParam Integer reviewId) {
        tourReviewService.deleteReview(reviewId);
        return ResponseEntity.ok(messageSource.getMessage("reviews.success.delete_review", null, Locale.ROOT));
    }
}
