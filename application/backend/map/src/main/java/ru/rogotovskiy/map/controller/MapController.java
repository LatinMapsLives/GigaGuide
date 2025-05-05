package ru.rogotovskiy.map.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.rogotovskiy.map.service.MapService;

@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;

    @GetMapping("/moment")
    public ResponseEntity<?> getMomentCoordinates(@RequestParam Integer id) {
        return ResponseEntity.ok(mapService.getMomentCoordinates(id));
    }

    @GetMapping("/sight")
    public ResponseEntity<?> getSightCoordinates(@RequestParam Integer id) {
        return ResponseEntity.ok(mapService.getSightCoordinates(id));
    }
}
