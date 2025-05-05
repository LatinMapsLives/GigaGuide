package ru.rogotovskiy.toursight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.rogotovskiy.toursight.dto.create.CreateMomentDto;
import ru.rogotovskiy.toursight.dto.response.SuccessResponse;
import ru.rogotovskiy.toursight.dto.update.UpdateMomentDto;
import ru.rogotovskiy.toursight.service.MomentService;

import java.io.IOException;

@RestController
@RequestMapping("/api/tour-sight/moments")
@RequiredArgsConstructor
public class MomentController {

    private final MomentService momentService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(momentService.getAll());
    }

    @GetMapping
    public ResponseEntity<?> getMomentById(@RequestParam Integer id) {
        return ResponseEntity.ok(momentService.getById(id));
    }

    @GetMapping("/sight")
    public ResponseEntity<?> getMomentsBySightId(@RequestParam Integer sightId) {
        return ResponseEntity.ok(momentService.getMomentsBySightId(sightId));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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

    @PutMapping
    public ResponseEntity<?> updateMoment(@RequestParam Integer id, @RequestBody UpdateMomentDto dto) {
        momentService.updateMoment(id, dto);
        return ResponseEntity.ok(new SuccessResponse("Момент обновлён успешно"));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMoment(@RequestParam Integer id) {
        momentService.deleteMoment(id);
        return ResponseEntity.ok(new SuccessResponse("Момент удалён успешно"));
    }
}
