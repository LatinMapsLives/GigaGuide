package ru.rogotovskiy.toursight.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.rogotovskiy.toursight.dto.create.CreateSightDto;
import ru.rogotovskiy.toursight.dto.read.PreviewSightDto;
import ru.rogotovskiy.toursight.dto.read.SightDto;
import ru.rogotovskiy.toursight.dto.update.UpdateSightDto;
import ru.rogotovskiy.toursight.entity.Sight;
import ru.rogotovskiy.toursight.exception.SightNotFoundException;
import ru.rogotovskiy.toursight.mapper.SightMapper;
import ru.rogotovskiy.toursight.repository.SightRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class SightService {

    private final String SIGHT_NOT_FOUND_TEMPLATE = "Достопримечательность с id = %d не найдена";

    private final SightRepository sightRepository;
    private final SightMapper sightMapper;
    private final ImageService imageService;

    public Sight getSightById(Integer id) {
        return sightRepository.findById(id).orElseThrow(
                () -> new SightNotFoundException(SIGHT_NOT_FOUND_TEMPLATE.formatted(id))
        );
    }

    public SightDto getById(Integer id) {
        return sightMapper.toDto(getSightById(id));
    }

    public void createSight(CreateSightDto dto, MultipartFile image) throws IOException {
        Sight sight = sightMapper.toEntity(dto);
        sight.setImagePath(imageService.saveImage(image, "sights"));
        sightRepository.save(sight);
    }

    public void updateSight(UpdateSightDto dto, MultipartFile image) throws IOException {
        Sight sight = getSightById(dto.id());
        sight.setName(dto.name());
        sight.setDescription(dto.description());
        sight.setCity(dto.city());
        sight.setLatitude(dto.latitude());
        sight.setLongitude(dto.longitude());
        if (image != null) {
            imageService.deleteImage(sight.getImagePath());
            sight.setImagePath(imageService.saveImage(image, "sights"));
        }
        sightRepository.save(sight);
    }

    public void deleteSight(Integer id) {
        Sight sight = getSightById(id);
        imageService.deleteImage(sight.getImagePath());
        sightRepository.delete(getSightById(id));
    }

    public List<SightDto> getAll() {
        return StreamSupport.stream(sightRepository.findAll().spliterator(), false)
                .map(sightMapper::toDto)
                .toList();
    }

    public List<PreviewSightDto> searchSights(String name) {
        return sightRepository.findByNameContainingIgnoreCase(name).stream()
                .map(sightMapper::toPreviewDto)
                .toList();
    }
}
