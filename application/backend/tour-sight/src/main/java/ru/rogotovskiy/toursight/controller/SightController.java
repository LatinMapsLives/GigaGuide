package ru.rogotovskiy.toursight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.rogotovskiy.toursight.dto.create.CreateSightDto;
import ru.rogotovskiy.toursight.dto.response.SuccessResponse;
import ru.rogotovskiy.toursight.dto.update.UpdateSightDto;
import ru.rogotovskiy.toursight.service.SightService;

import java.io.IOException;

@RestController
@RequestMapping("/api/tour-sight/sights")
@RequiredArgsConstructor
public class SightController {

    private final SightService sightService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(sightService.getAll());
    }

    @GetMapping
    public ResponseEntity<?> getSightById(@RequestParam Integer id) {
        return ResponseEntity.ok(sightService.getById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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

    @PutMapping
    public ResponseEntity<?> updateSight(@RequestParam Integer id, @RequestBody UpdateSightDto dto) {
        sightService.updateSight(id, dto);
        return ResponseEntity.ok(new SuccessResponse("Достопримечательность успешно обновлена"));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSight(@RequestParam Integer id) {
        sightService.deleteSight(id);
        return ResponseEntity.ok(new SuccessResponse("Достопримечательность успешно удалена"));
    }
}
