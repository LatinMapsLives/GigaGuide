package ru.rogotovskiy.map.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rogotovskiy.map.service.TourMapService;

@RestController
@RequestMapping("/api/map/tour")
@RequiredArgsConstructor
public class TourMapController {

    private final TourMapService tourMapService;

    @PostMapping
    public ResponseEntity<?> getRouteInfo(@RequestParam Integer tourId) {
        tourMapService.addTourRouteInfo(tourId);
        return ResponseEntity.ok("Протяжённость и длительность успешно расчитаны");
    }
}
