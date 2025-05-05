package ru.rogotovskiy.toursight.controller;

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
public class ImageController {

    private final ImageService imageService;

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
