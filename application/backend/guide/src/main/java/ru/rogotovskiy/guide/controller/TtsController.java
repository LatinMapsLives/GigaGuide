package ru.rogotovskiy.guide.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.rogotovskiy.guide.service.AudioGuideService;

import java.io.IOException;

@RestController
@RequestMapping("/api/guide")
@RequiredArgsConstructor
@Tag(name = "Аудиогиды", description = "Генерация аудиогида из текста")
public class TtsController {

    private final AudioGuideService audioGuideService;

    @Operation(summary = "Сгенерировать аудиогид")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Аудиогид успешно сгенерирован"),
            @ApiResponse(responseCode = "404", description = "Момент с указанным ID не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера при генерации аудиогида")
    })
    @Parameter(
            name = "id",
            description = "Уникальный идентификатор момента, для которого требуется озвучка",
            required = true,
            example = "1"
    )
    @GetMapping(produces = "audio/wav")
    public ResponseEntity<byte[]> getAudio(@RequestParam Integer id) throws IOException {
        byte[] audio = audioGuideService.getAudioForMoment(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=guide.wav")
                .contentType(MediaType.parseMediaType("audio/wav"))
                .body(audio);
    }
}
