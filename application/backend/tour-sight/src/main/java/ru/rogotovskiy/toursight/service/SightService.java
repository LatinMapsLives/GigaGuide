package ru.rogotovskiy.toursight.service;

import com.amazonaws.services.kms.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.rogotovskiy.toursight.dto.create.CreateSightDto;
import ru.rogotovskiy.toursight.dto.read.PreviewSightDto;
import ru.rogotovskiy.toursight.dto.read.SightDto;
import ru.rogotovskiy.toursight.dto.update.UpdateSightDto;
import ru.rogotovskiy.toursight.entity.Sight;
import ru.rogotovskiy.toursight.entity.SightTranslation;
import ru.rogotovskiy.toursight.exception.SightNotFoundException;
import ru.rogotovskiy.toursight.mapper.SightMapper;
import ru.rogotovskiy.toursight.repository.SightRepository;
import ru.rogotovskiy.toursight.repository.SightTranslationRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class SightService {

    private final String SIGHT_NOT_FOUND_TEMPLATE = "Достопримечательность с id = %d не найдена";

    private final SightRepository sightRepository;
    private final SightTranslationService sightTranslationService;
    private final SightMapper sightMapper;
    private final ImageService imageService;

    public Sight getSightById(Integer id) {
        return sightRepository.findById(id).orElseThrow(
                () -> new SightNotFoundException(SIGHT_NOT_FOUND_TEMPLATE.formatted(id))
        );
    }

    public SightDto getById(Integer id, String languageCode) {
        return sightMapper.toDto(getSightById(id), sightTranslationService.getTranslation(id, languageCode));
    }

    public void createSight(CreateSightDto dto, MultipartFile image) throws IOException {
        Sight sight = sightMapper.toEntity(dto);
        sight.setImagePath(imageService.saveImage(image, "sights"));
        sightRepository.save(sight);
        sightTranslationService.createSightTranslation(dto, sight.getId());
    }

    public void updateSight(UpdateSightDto dto, MultipartFile image) throws IOException {
        Sight sight = getSightById(dto.id());
        if (dto.latitude() != null) {
            sight.setLatitude(dto.latitude());
        }
        if (dto.longitude() != null) {
            sight.setLongitude(dto.longitude());
        }
        if (image != null) {
            imageService.deleteImage(sight.getImagePath());
            sight.setImagePath(imageService.saveImage(image, "sights"));
        }
        sightRepository.save(sight);
        sightTranslationService.updateSightTranslation(dto, sight.getId());
    }

    public void deleteSight(Integer id) {
        Sight sight = getSightById(id);
        imageService.deleteImage(sight.getImagePath());
        sightRepository.delete(getSightById(id));
    }

    public List<SightDto> getAll(String languageCode) {
        return sightRepository.findAll().stream()
                .map(sight -> sightMapper.toDto(
                        sight,
                        sightTranslationService.getTranslation(sight.getId(), languageCode))
                ).toList();
    }

    public List<PreviewSightDto> searchSights(String name, String languageCode) {
        return sightTranslationService.searchSightsTranslation(name, languageCode).stream()
                .map(translation -> sightMapper.toPreviewDto(
                        getSightById(translation.getSightId()),
                        translation
                )).toList();
    }

    public PreviewSightDto getPreviewSightDto(Integer sightId, String languageCode) {
        return sightMapper.toPreviewDto(
                getSightById(sightId),
                sightTranslationService.getTranslation(sightId, languageCode)
        );
    }
}
