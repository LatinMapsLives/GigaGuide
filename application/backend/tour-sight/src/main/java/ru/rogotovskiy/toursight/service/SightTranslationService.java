package ru.rogotovskiy.toursight.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.toursight.dto.create.CreateSightDto;
import ru.rogotovskiy.toursight.dto.update.UpdateSightDto;
import ru.rogotovskiy.toursight.entity.Language;
import ru.rogotovskiy.toursight.entity.Sight;
import ru.rogotovskiy.toursight.entity.SightTranslation;
import ru.rogotovskiy.toursight.mapper.SightTranslationMapper;
import ru.rogotovskiy.toursight.repository.SightTranslationRepository;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SightTranslationService {

    private final SightTranslationRepository repository;
    private final LanguageService languageService;
    private final SightTranslationMapper mapper;
    private final TranslationService translationService;

    public SightTranslation getTranslation(Integer id, String languageCode) {
        return repository.findBySightIdAndLanguage_Code(id, languageCode).orElseThrow(
                () -> new NoSuchElementException("Не найден")
        );
    }

    public List<SightTranslation> searchSightsTranslation(String name, String languageCode) {
        String query = "%" + name.toLowerCase() + "%";
        return repository.findByNameOrCityLikeIgnoreCaseAndLanguage(query, languageCode);
    }

    public void createSightTranslation(CreateSightDto dto, Integer sightId) {
        List<Language> languages = languageService.getAll();
        for (Language language : languages) {
            createSightTranslation(dto, sightId, language);
        }
    }

    public void createSightTranslation(CreateSightDto dto, Integer sightId, Language language) {
        if (language.getCode().equalsIgnoreCase("ru")) {
            repository.save(mapper.toEntity(dto, sightId, language));
        } else {
            repository.save(translateSight(dto, sightId, language));
        }
    }

    public SightTranslation translateSight(CreateSightDto dto, Integer sightId, Language language) {
        try {
            return new SightTranslation(
                    null,
                    sightId,
                    language,
                    translationService.translate(dto.name(), language.getCode()),
                    translationService.translate(dto.description(), language.getCode()),
                    translationService.translate(dto.city(), language.getCode())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateSightTranslation(UpdateSightDto dto, Integer sightId) {
        List<SightTranslation> translations = repository.findBySightId(sightId);
        if (dto.name() != null && !dto.name().isEmpty()) {
            for (SightTranslation translation : translations) {
                try {
                    translation.setName(translationService.translate(dto.name(), translation.getLanguage().getCode()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (dto.description() != null && !dto.description().isEmpty()) {
            for (SightTranslation translation : translations) {
                try {
                    translation.setDescription(translationService.translate(dto.description(), translation.getLanguage().getCode()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (dto.city() != null && !dto.city().isEmpty()) {
            for (SightTranslation translation : translations) {
                try {
                    translation.setCity(translationService.translate(dto.city(), translation.getLanguage().getCode()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        repository.saveAll(translations);
    }
}
