package ru.rogotovskiy.map.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.rogotovskiy.map.dto.CoordinatesDto;
import ru.rogotovskiy.map.service.MapService;

@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
@Tag(name = "Карта", description = "Получение координат моментов и достопримечательностей")
public class MapController {

    private final MapService mapService;

    @Operation(summary = "Получить координаты момента", description = "Возвращает широту и долготу момента по его ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно получены координаты момента",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CoordinatesDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Момент с указанным ID не найден"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера"
            )
    })
    @Parameter(
            name = "id",
            description = "ID момента",
            required = true,
            example = "1"
    )
    @GetMapping("/moment")
    public ResponseEntity<?> getMomentCoordinates(@RequestParam Integer id) {
        return ResponseEntity.ok(mapService.getMomentCoordinates(id));
    }

    @Operation(
            summary = "Получить координаты достопримечательности",
            description = "Возвращает широту и долготу достопримечательности по её ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно получены координаты достопримечательности",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CoordinatesDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Достопримечательность с указанным ID не найдена"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера"
            )
    })
    @Parameter(
            name = "id",
            description = "ID достопримечательности",
            required = true,
            example = "1"
    )
    @GetMapping("/sight")
    public ResponseEntity<?> getSightCoordinates(@RequestParam Integer id) {
        return ResponseEntity.ok(mapService.getSightCoordinates(id));
    }
}
