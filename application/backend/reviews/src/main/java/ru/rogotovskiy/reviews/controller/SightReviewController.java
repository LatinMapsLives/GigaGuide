package ru.rogotovskiy.reviews.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rogotovskiy.reviews.dto.create.CreateSightReviewDto;
import ru.rogotovskiy.reviews.dto.read.SightReviewDto;
import ru.rogotovskiy.reviews.service.SightReviewService;

import java.util.Locale;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(
        name = "Отзывы о достопримечательностях",
        description = "Операции для получения, добавления и удаления отзывов о достопримечательностях"
)
public class SightReviewController {

    private final SightReviewService service;
    private final MessageSource messageSource;

    @Operation(
            summary = "Получить все отзывы о достопримечательности",
            description = "Возвращает список всех отзывов о достопримечательности"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список отзывов успешно получен",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = SightReviewDto.class)))
    )
    @GetMapping("/sights")
    public ResponseEntity<?> getAll(@RequestParam Integer sightId, @RequestHeader(value = "X-User-Id", required = false) String userId) {
        return ResponseEntity.ok(service.getAll(sightId, userId));
    }

    @Operation(
            summary = "Добавить отзыв о достопримечательности",
            description = "Добавляет отзыв от пользователя к указанной достопримечательности"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Отзыв успешно добавлен"
    )
    @PostMapping("/sights")
    public ResponseEntity<?> addReview(@RequestHeader("X-User-Id") String userId, @RequestParam Integer sightId,
                                       @RequestBody CreateSightReviewDto dto) {
        service.addReview(Integer.parseInt(userId), sightId, dto);
        return ResponseEntity.ok(messageSource.getMessage("reviews.success.add_review", null, Locale.ROOT));
    }

    @Operation(
            summary = "Удалить отзыв о достопримечательности",
            description = "Удаляет отзыв, принадлежащий пользователю"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Отзыв успешно удалён"
    )
    @DeleteMapping("/sights")
    public ResponseEntity<?> deleteReview(@RequestHeader("X-User-Id") String userId, @RequestParam Integer reviewId) {
        service.deleteReview(Integer.parseInt(userId), reviewId);
        return ResponseEntity.ok(messageSource.getMessage("reviews.success.delete_review", null, Locale.ROOT));
    }

    @DeleteMapping("/admin/sights")
    public ResponseEntity<?> adminDeleteReview(@RequestParam Integer reviewId) {
        service.deleteReview(reviewId);
        return ResponseEntity.ok(messageSource.getMessage("reviews.success.delete_review", null, Locale.ROOT));
    }
}
