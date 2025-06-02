package ru.rogotovskiy.map.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rogotovskiy.map.dto.RouteInfoDto;
import ru.rogotovskiy.map.entity.Sight;
import ru.rogotovskiy.map.entity.Tour;
import ru.rogotovskiy.map.repository.TourRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourMapService {

    private final TourRepository tourRepository;
    private final TourTranslationService tourTranslationService;
    private final RouteService routeService;

    public void addTourRouteInfo(Integer tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new EntityNotFoundException("Тур с id " + tourId + " не найден"));

        List<Sight> sights = tour.getSights();
        String routeType = tourTranslationService.getTourTranslation(tourId).getType();
        RouteInfoDto dto = routeService.calculateRouteInfo(sights, routeType);
        tour.setDistanceKm(dto.distanceKm());
        tour.setDurationMinutes(dto.durationMinutes());
        tourRepository.save(tour);
    }
}
