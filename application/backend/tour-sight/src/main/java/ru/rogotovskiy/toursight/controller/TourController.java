package ru.rogotovskiy.toursight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.rogotovskiy.toursight.dto.create.CreateTourDto;
import ru.rogotovskiy.toursight.dto.read.TourDto;
import ru.rogotovskiy.toursight.dto.response.ErrorResponse;
import ru.rogotovskiy.toursight.dto.response.SuccessResponse;
import ru.rogotovskiy.toursight.dto.update.UpdateTourDto;
import ru.rogotovskiy.toursight.service.TourService;

@RestController
@RequestMapping("/api/tour-sight/tours")
@RequiredArgsConstructor
@Tag(name = "Туры", description = "Просмотр, добавление, обновление и удаление туров")
public class TourController {

    private final TourService tourService;

    @Hidden
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(tourService.getAll());
    }

    @Operation(summary = "Получить тур по ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Тур успешно получен",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TourDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Тур не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @Parameter(name = "id", description = "ID тура")
    @GetMapping
    public ResponseEntity<?> getTourById(@RequestParam Integer id) {
        return ResponseEntity.ok(tourService.getById(id));
    }

    @Operation(summary = "Создать новый тур")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Тур успешно создан",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера"
            )
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createTour(@RequestPart(value = "tour") String json,
                                        @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            CreateTourDto dto = mapper.readValue(json, CreateTourDto.class);
            tourService.createTour(dto, image);
            return ResponseEntity.ok("Тур успешно создан");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Обновить данные тура")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Тур успешно обновлён",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Тур не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateTour(@RequestParam(value = "tour") String updateTourJson,
                                        @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            UpdateTourDto dto = mapper.readValue(updateTourJson, UpdateTourDto.class);
            tourService.updateTour(dto, image);
            return ResponseEntity.ok("Тур обновлён создан");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Удалить тур")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Тур успешно удалён",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Тур не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @Parameter(name = "id", description = "ID тура")
    @DeleteMapping
    public ResponseEntity<?> deleteTour(@RequestParam Integer id) {
        tourService.deleteTour(id);
        return ResponseEntity.ok("Тур успешно удалён");
    }

    @Operation(summary = "Поиск туров по названию")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Туры найдены"
            )
    })
    @Parameter(name = "name", description = "Имя тура")
    @GetMapping("/search")
    public ResponseEntity<?> searchTours(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(tourService.searchTours(name));
    }

    @Operation(summary = "Фильтрация туров по категории, длительности и расстоянию")
    @GetMapping("/filter")
    public ResponseEntity<?> filterTours(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer minDuration,
            @RequestParam(required = false) Integer maxDuration,
            @RequestParam(required = false) Double minDistance,
            @RequestParam(required = false) Double maxDistance
    ) {
        return ResponseEntity.ok(
                tourService.filterTours(category, minDuration, maxDuration, minDistance, maxDistance)
        );
    }
}
