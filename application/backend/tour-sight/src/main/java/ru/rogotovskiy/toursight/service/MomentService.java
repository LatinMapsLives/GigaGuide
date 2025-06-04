package ru.rogotovskiy.toursight.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.rogotovskiy.toursight.dto.create.CreateMomentDto;
import ru.rogotovskiy.toursight.dto.read.MomentDto;
import ru.rogotovskiy.toursight.dto.update.UpdateMomentDto;
import ru.rogotovskiy.toursight.entity.Moment;
import ru.rogotovskiy.toursight.exception.MomentNotFoundException;
import ru.rogotovskiy.toursight.mapper.MomentMapper;
import ru.rogotovskiy.toursight.repository.MomentRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class MomentService {

    private final String MOMENT_NOT_FOUND_TEMPLATE = "Момент с id = %d не найден";

    private final MomentRepository momentRepository;
    private final MomentMapper momentMapper;
    private final SightService sightService;
    private final MomentTranslationService momentTranslationService;
    private final ImageService imageService;


    public MomentDto getById(Integer id, String languageCode) {
        return momentMapper.toDto(getMomentById(id), momentTranslationService.getMomentTranslation(id, languageCode));
    }

    private Moment getMomentById(Integer id) {
        return momentRepository.findById(id).orElseThrow(
                () -> new MomentNotFoundException(MOMENT_NOT_FOUND_TEMPLATE.formatted(id))
        );
    }

    public void createMoment(CreateMomentDto dto, MultipartFile image) throws IOException {
        Moment moment = momentMapper.toEntity(dto);
        moment.setSight(sightService.getSightById(dto.sightId()));
        moment.setImagePath(imageService.saveImage(image, "moments"));
        momentRepository.save(moment);
        momentTranslationService.createMomentTranslation(dto, moment.getId());
    }

    public void deleteMoment(Integer id) {
        Moment moment = getMomentById(id);
        imageService.deleteImage(moment.getImagePath());
        momentRepository.delete(moment);
    }

    public void updateMoment(UpdateMomentDto dto, MultipartFile image) throws IOException {
        Moment moment = getMomentById(dto.id());
        if (dto.orderNumber() != null) {
            moment.setOrderNumber(dto.orderNumber());
        }
        if (dto.latitude() != null) {
            moment.setLatitude(dto.latitude());
        }
        if (dto.longitude() != null) {
            moment.setLongitude(dto.longitude());
        }
        if (image != null) {
            imageService.deleteImage(moment.getImagePath());
            moment.setImagePath(imageService.saveImage(image, "moments"));
        }
        momentRepository.save(moment);
        momentTranslationService.updateMomentTranslation(dto, moment.getId());
    }

    public List<MomentDto> getAll(String language) {
        return momentRepository.findAll().stream()
                .map(moment -> momentMapper.toDto(
                        moment,
                        momentTranslationService.getMomentTranslation(moment.getId(), language)
                )).toList();
    }

    public List<MomentDto> getMomentsBySightId(Integer sightId, String languageCode) {
        return momentRepository.findMomentsBySightIdOrderByOrderNumberAsc(sightId).stream()
                .map(moment -> momentMapper.toDto(
                        moment,
                        momentTranslationService.getMomentTranslation(moment.getId(), languageCode)
                )).toList();
    }
}
