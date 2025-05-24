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
    private final ImageService imageService;


    public MomentDto getById(Integer id) {
        return momentMapper.toDto(getMomentById(id));
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
    }

    public void deleteMoment(Integer id) {
        Moment moment = getMomentById(id);
        imageService.deleteImage(moment.getImagePath());
        momentRepository.delete(moment);
    }

    public void updateMoment(UpdateMomentDto dto, MultipartFile image) throws IOException {
        Moment moment = getMomentById(dto.id());
        moment.setName(dto.name());
        moment.setOrderNumber(dto.orderNumber());
        moment.setContent(dto.content());
        moment.setLatitude(dto.latitude());
        moment.setLongitude(dto.longitude());
        if (image != null) {
            imageService.deleteImage(moment.getImagePath());
            moment.setImagePath(imageService.saveImage(image, "moments"));
        }
        momentRepository.save(moment);
    }

    public List<MomentDto> getAll() {
        return StreamSupport.stream(momentRepository.findAll().spliterator(), false)
                .map(momentMapper::toDto)
                .toList();
    }

    public List<MomentDto> getMomentsBySightId(Integer sightId) {
        return momentRepository.findMomentsBySightIdOrderByOrderNumberAsc(sightId).stream()
                .map(momentMapper::toDto)
                .toList();
    }
}
