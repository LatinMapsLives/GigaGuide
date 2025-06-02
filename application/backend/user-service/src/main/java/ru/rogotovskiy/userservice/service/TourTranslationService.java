package ru.rogotovskiy.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.userservice.entity.TourTranslation;
import ru.rogotovskiy.userservice.repository.TourTranslationRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TourTranslationService {

    private final TourTranslationRepository repository;

    public TourTranslation getTourTranslation(Integer tourId, String languageCode) {
        return repository.findByTourIdAndLanguage_Code(tourId, languageCode).orElseThrow(
                () -> new NoSuchElementException("Не найден")
        );
    }
}
