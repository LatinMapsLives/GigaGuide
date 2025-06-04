package ru.rogotovskiy.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.userservice.entity.SightTranslation;
import ru.rogotovskiy.userservice.repository.SightTranslationRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SightTranslationService {

    private final SightTranslationRepository repository;

    public SightTranslation getSighTranslation(Integer sightId, String languageCode) {
        return repository.findBySightIdAndLanguage_Code(sightId, languageCode).orElseThrow(
                () -> new NoSuchElementException("Не найден")
        );
    }
}
