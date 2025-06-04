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
import ru.rogotovskiy.toursight.dto.create.CreateSightDto;
import ru.rogotovskiy.toursight.dto.read.SightDto;
import ru.rogotovskiy.toursight.dto.response.ErrorResponse;
import ru.rogotovskiy.toursight.dto.response.SuccessResponse;
import ru.rogotovskiy.toursight.dto.update.UpdateSightDto;
import ru.rogotovskiy.toursight.service.SightService;

import java.io.IOException;

@RestController
@RequestMapping("/api/tour-sight")
@RequiredArgsConstructor
@Tag(name = "Достопримечательности", description = "Просмотр, добавление, обновление и удаление достопримечательностей")
public class SightController {

    private final SightService sightService;

    @Hidden
    @GetMapping("/sights/all")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "ru") String language) {
        return ResponseEntity.ok(sightService.getAll(language));
    }

    @Operation(summary = "Получить достопримечательность по ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Достопримечательность успешно получена",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SightDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Достопримечательность не найдена",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @Parameter(name = "id", description = "ID достопримечательности")
    @GetMapping("/sights")
    public ResponseEntity<?> getSightById(@RequestParam Integer id, @RequestParam(defaultValue = "ru") String language) {
        return ResponseEntity.ok(sightService.getById(id, language));
    }

    @Operation(summary = "Создать новую достопримечательность")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Достопримечательность успешно создана",
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
    @PostMapping(value = "/admin/sights", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createSight(@RequestParam(value = "sight") String sightJson,
                                         @RequestParam(value = "image", required = false) MultipartFile image) {
        ObjectMapper mapper = new ObjectMapper();
        CreateSightDto dto = null;
        try {
            dto = mapper.readValue(sightJson, CreateSightDto.class);
            sightService.createSight(dto, image);
            return ResponseEntity.ok(new SuccessResponse("Достопримечательность успешно создана"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Обновить данные достопримечательности")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Достопримечательность успешно обновлена",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Достопримечательность не найдена",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @PutMapping(value = "/admin/sights", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateSight(@RequestParam(value = "sight") String updateSightJson,
                                         @RequestParam(value = "image", required = false) MultipartFile image) {
        ObjectMapper mapper = new ObjectMapper();
        UpdateSightDto dto = null;
        try {
            dto = mapper.readValue(updateSightJson, UpdateSightDto.class);
            sightService.updateSight(dto, image);
            return ResponseEntity.ok(new SuccessResponse("Достопримечательность успешно обновлена"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Удалить достопримечательность")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Достопримечательность успешно удалена",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Достопримечательность не найдена",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @DeleteMapping("/admin/sights")
    public ResponseEntity<?> deleteSight(@RequestParam Integer id) {
        sightService.deleteSight(id);
        return ResponseEntity.ok(new SuccessResponse("Достопримечательность успешно удалена"));
    }

    @Operation(summary = "Поиск достопримечательностей по имени")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Достопримечательности успешно найдены"
            )
    })
    @Parameter(name = "name", description = "Название достопримечательности")
    @GetMapping("/sights/search")
    public ResponseEntity<?> searchSights(@RequestParam(required = false) String name,
                                          @RequestParam(defaultValue = "ru") String language) {
        return ResponseEntity.ok(sightService.searchSights(name, language));
    }
}
