package ru.rogotovskiy.map.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.map.dto.CoordinatesDto;
import ru.rogotovskiy.map.mapper.MapMapper;
import ru.rogotovskiy.map.repository.MomentRepository;
import ru.rogotovskiy.map.repository.SightRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MapService {

    private final MomentRepository momentRepository;
    private final SightRepository sightRepository;
    private final MapMapper mapper;


    public CoordinatesDto getMomentCoordinates(Integer id) {
        return mapper.toDto(momentRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Момент с id %d не найден".formatted(id))
        ));
    }

    public CoordinatesDto getSightCoordinates(Integer id) {
        return mapper.toDto(sightRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Достопримечательность с id %d не найдена".formatted(id))
        ));
    }
}
