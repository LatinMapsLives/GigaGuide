package ru.rogotovskiy.toursight.service;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.rogotovskiy.toursight.dto.create.CreateTourDto;
import ru.rogotovskiy.toursight.dto.read.PreviewTourDto;
import ru.rogotovskiy.toursight.dto.read.TourDto;
import ru.rogotovskiy.toursight.dto.update.UpdateTourDto;
import ru.rogotovskiy.toursight.entity.Tour;
import ru.rogotovskiy.toursight.entity.TourTranslation;
import ru.rogotovskiy.toursight.exception.TourNotFoundException;
import ru.rogotovskiy.toursight.mapper.SightMapper;
import ru.rogotovskiy.toursight.mapper.TourMapper;
import ru.rogotovskiy.toursight.repository.TourRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TourService {

    private final String TOUR_NOT_FOUND_TEMPLATE = "Тур с id = %d не найден";

    private final TourRepository tourRepository;
    private final TourMapper tourMapper;
    private final ImageService imageService;
    private final SightService sightService;
    private final TourTranslationService tourTranslationService;
    private final RestTemplate restTemplate;

    public List<TourDto> getAll(String languageCode) {
        List<Tour> tours = tourRepository.findAll();
        return tours.stream()
                .map(tour -> getById(tour.getId(), languageCode))
                .toList();
    }

    private Tour getTourById(Integer id) {
        return tourRepository.findById(id).orElseThrow(
                () -> new TourNotFoundException(TOUR_NOT_FOUND_TEMPLATE.formatted(id))
        );
    }

    public TourDto getById(Integer id, String languageCode) {
        Tour tour = getTourById(id);
        TourDto tourDto = tourMapper.toDto(tour, tourTranslationService.getTranslation(tour.getId(), languageCode));
        tourDto.setSights(tour.getSights().stream()
                .map(sight -> sightService.getPreviewSightDto(sight.getId(), languageCode))
                .toList());
        return tourDto;
    }

    public void createTour(CreateTourDto dto, MultipartFile image) throws IOException {
        Tour tour = tourMapper.toEntity(dto);
        tour.setSights(dto.getSights().stream()
                .map(sightService::getSightById)
                .toList());
        tour.setImagePath(imageService.saveImage(image, "tours"));
        tourRepository.save(tour);
        tourTranslationService.createTourTranslation(dto, tour.getId());
        String url = String.format("%s/api/map/tour?tourId=%d", "http://localhost:8086", tour.getId());
        try {
            restTemplate.postForEntity(url, null, Void.class);
        } catch (Exception e) {
            System.err.println("Ошибка при расчёте маршрута: " + e.getMessage());
        }
    }

    public void updateTour(UpdateTourDto dto, MultipartFile image) throws IOException {
        Tour tour = getTourById(dto.id());
        if (image != null) {
            imageService.deleteImage(tour.getImagePath());
            tour.setImagePath(imageService.saveImage(image, "tours"));
        }
        tourRepository.save(tour);
        tourTranslationService.updateTourTranslation(dto, tour.getId());
    }

    public void deleteTour(Integer id) {
        Tour tour = getTourById(id);
        imageService.deleteImage(tour.getImagePath());
        tourRepository.delete(tour);
    }

    public List<PreviewTourDto> searchAndFilterTours(
            String query,
            String category,
            Integer minDuration,
            Integer maxDuration,
            Double minDistance,
            Double maxDistance,
            String language
    ) {
        List<TourTranslation> translations = tourTranslationService.findByLanguage(language).stream()
                .filter(t -> {
                    boolean matchesQuery = true;
                    boolean matchesCategory = true;

                    if (query != null && !query.isBlank()) {
                        String q = query.toLowerCase();
                        matchesQuery = (t.getName() != null && t.getName().toLowerCase().contains(q))
                                || (t.getDescription() != null && t.getDescription().toLowerCase().contains(q))
                                || (t.getCity() != null && t.getCity().toLowerCase().contains(q));
                    }

                    if (category != null && !category.isBlank()) {
                        matchesCategory = t.getCategory() != null && t.getCategory().equalsIgnoreCase(category);
                    }

                    return matchesQuery && matchesCategory;
                })
                .toList();

        Set<Integer> matchingTourIds = translations.stream()
                .map(TourTranslation::getTourId)
                .collect(Collectors.toSet());

        if (matchingTourIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<Tour> filteredTours = tourRepository.findAll((root, query1, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(root.get("id").in(matchingTourIds));

            if (minDuration != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("durationMinutes"), minDuration));
            }

            if (maxDuration != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("durationMinutes"), maxDuration));
            }

            if (minDistance != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("distanceKm"), BigDecimal.valueOf(minDistance)));
            }

            if (maxDistance != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("distanceKm"), BigDecimal.valueOf(maxDistance)));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
        return filteredTours.stream()
                .map(tour -> {
                    TourTranslation translation = translations.stream()
                            .filter(t -> t.getTourId().equals(tour.getId()))
                            .findFirst()
                            .orElse(null);

                    return tourMapper.toPreviewDto(tour, translation);
                })
                .toList();
    }
}
