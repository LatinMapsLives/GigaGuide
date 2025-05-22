package ru.rogotovskiy.toursight.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rogotovskiy.toursight.service.ImageService;

import java.io.IOException;

@RestController
@RequestMapping("/api/tour-sight/image")
@RequiredArgsConstructor
@Tag(name = "Изображения", description = "Получение изображений туров, достопримечательностей и моментов")
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "Получение изображения по имени файла")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Изображение получено"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера"
            )
    })
    @Parameter(description = "Имя файла изображения")
    @GetMapping
    public ResponseEntity<?> getImage(@RequestParam String fileName) {
        try {
            byte[] image = imageService.getImage(fileName);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new ByteArrayResource(image));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
