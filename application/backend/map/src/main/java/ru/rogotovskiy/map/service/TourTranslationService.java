package ru.rogotovskiy.map.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.map.entity.TourTranslation;
import ru.rogotovskiy.map.repository.TourTranslationRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TourTranslationService {

    private final TourTranslationRepository repository;

    public TourTranslation getTourTranslation(Integer tourId) {
        return repository.findByTourIdAndLanguage_Code(tourId, "ru").orElseThrow(
                () -> new NoSuchElementException("Не найден")
        );
    }
}
