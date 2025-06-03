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
import ru.rogotovskiy.toursight.dto.create.CreateMomentDto;
import ru.rogotovskiy.toursight.dto.read.MomentDto;
import ru.rogotovskiy.toursight.dto.response.ErrorResponse;
import ru.rogotovskiy.toursight.dto.response.SuccessResponse;
import ru.rogotovskiy.toursight.dto.update.UpdateMomentDto;
import ru.rogotovskiy.toursight.service.MomentService;

import java.io.IOException;

@RestController
@RequestMapping("/api/tour-sight")
@RequiredArgsConstructor
@Tag(name = "Моменты", description = "Методы для работы с моментами")
public class MomentController {

    private final MomentService momentService;

    @Hidden
    @GetMapping("/moments/all")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "ru") String language) {
        return ResponseEntity.ok(momentService.getAll(language));
    }

    @Operation(summary = "Получить момент по ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Момент получен",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MomentDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Момент не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @Parameter(name = "id", description = "ID момента")
    @GetMapping("/moments")
    public ResponseEntity<?> getMomentById(@RequestParam Integer id, @RequestParam(defaultValue = "ru") String language) {
        return ResponseEntity.ok(momentService.getById(id, language));
    }

    @Operation(summary = "Получить список моментов по ID достопримечательности")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Список моментов успешно получен"
            )
    })
    @Parameter(name = "id", description = "ID достопримечательности")
    @GetMapping("/moments/sight")
    public ResponseEntity<?> getMomentsBySightId(@RequestParam Integer sightId,
                                                 @RequestParam(defaultValue = "ru") String language) {
        return ResponseEntity.ok(momentService.getMomentsBySightId(sightId, language));
    }

    @Operation(summary = "Создать новый момент")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Момент успешно создан",
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
    @PostMapping(value = "/admin/moments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createMoment(@RequestPart(value = "moment") String momentJson,
                                          @RequestPart(value = "image", required = false) MultipartFile image) {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateMomentDto dto = null;
        try {
            dto = objectMapper.readValue(momentJson, CreateMomentDto.class);
            momentService.createMoment(dto, image);
            return ResponseEntity.ok(new SuccessResponse("Момент создан успешно"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Обновить момент")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Момент обновлён успешно",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Момент не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @PutMapping(value = "/admin/moments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateMoment(@RequestParam(value = "moment") String momentUpdateJson,
                                          @RequestParam(value = "image", required = false) MultipartFile image) {
        ObjectMapper objectMapper = new ObjectMapper();
        UpdateMomentDto dto = null;
        try {
            dto = objectMapper.readValue(momentUpdateJson, UpdateMomentDto.class);
            momentService.updateMoment(dto, image);
            return ResponseEntity.ok(new SuccessResponse("Момент обновлён успешно"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Удалить момент")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Момент удалён успешно",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Момент не найден",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @DeleteMapping("/admin/moments")
    public ResponseEntity<?> deleteMoment(@RequestParam Integer id) {
        momentService.deleteMoment(id);
        return ResponseEntity.ok(new SuccessResponse("Момент удалён успешно"));
    }
}
