package ru.rogotovskiy.toursight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.rogotovskiy.toursight.dto.create.CreateTourDto;
import ru.rogotovskiy.toursight.dto.update.UpdateTourDto;
import ru.rogotovskiy.toursight.service.TourService;

@RestController
@RequestMapping("/api/tour-sight/tours")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(tourService.getAll());
    }

    @GetMapping
    public ResponseEntity<?> getTourById(@RequestParam Integer id) {
        return ResponseEntity.ok(tourService.getById(id));
    }

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

    @PutMapping
    public ResponseEntity<?> updateTour(@RequestParam Integer id, @RequestBody UpdateTourDto dto) {
        tourService.updateTour(id, dto);
        return ResponseEntity.ok("Тур успешно обновлён");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTour(@RequestParam Integer id) {
        tourService.deleteTour(id);
        return ResponseEntity.ok("Тур успешно удалён");
    }
}
