package ru.rogotovskiy.toursight.service;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.rogotovskiy.toursight.dto.create.CreateTourDto;
import ru.rogotovskiy.toursight.dto.read.TourDto;
import ru.rogotovskiy.toursight.dto.update.UpdateTourDto;
import ru.rogotovskiy.toursight.entity.Tour;
import ru.rogotovskiy.toursight.exception.TourNotFoundException;
import ru.rogotovskiy.toursight.mapper.TourMapper;
import ru.rogotovskiy.toursight.repository.TourRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TourService {

    private final String TOUR_NOT_FOUND_TEMPLATE = "Тур с id = %d не найден";

    private final TourRepository tourRepository;
    private final TourMapper tourMapper;
    private final ImageService imageService;

    public List<TourDto> getAll() {
        return StreamSupport.stream(tourRepository.findAll().spliterator(), false)
                .map(tourMapper::toDto)
                .toList();
    }

    private Tour getTourById(Integer id) {
        return tourRepository.findById(id).orElseThrow(
                () -> new TourNotFoundException(TOUR_NOT_FOUND_TEMPLATE.formatted(id))
        );
    }

    public TourDto getById(Integer id) {
        return tourMapper.toDto(getTourById(id));
    }

    public void createTour(CreateTourDto dto, MultipartFile image) throws IOException {
        Tour tour = tourMapper.toEntity(dto);
        tour.setImagePath(imageService.saveImage(image, "tours"));
        tourRepository.save(tour);
    }

    public void updateTour(Integer id, UpdateTourDto dto) {
        Tour tour = getTourById(id);
        tour.setName(dto.name());
        tour.setDescription(dto.description());
        tour.setCity(dto.city());
        tour.setCategory(dto.category());
        tour.setType(dto.type());
        tourRepository.save(tour);
    }

    public void deleteTour(Integer id) {
        Tour tour = getTourById(id);
        tourRepository.delete(tour);
    }

    public List<TourDto> searchTours(String name) {
        return tourRepository.findByNameContainingIgnoreCase(name).stream()
                .map(tourMapper::toDto)
                .toList();
    }

    public List<TourDto> filterTours(String category, Integer minDuration, Integer maxDuration, Double minDistance, Double maxDistance) {
        return tourRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (category != null && !category.isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("category")), category.toLowerCase()));
            }
            if (minDuration != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("duration"), minDuration));
            }
            if (maxDuration != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("duration"), maxDuration));
            }
            if (minDistance != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("distance"), minDistance));
            }
            if (maxDistance != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("distance"), maxDistance));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }).stream()
                .map(tourMapper::toDto)
                .toList();
    }
}
