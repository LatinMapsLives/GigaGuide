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
}
