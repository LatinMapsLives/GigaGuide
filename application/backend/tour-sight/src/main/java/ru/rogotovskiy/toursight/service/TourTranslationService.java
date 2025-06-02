package ru.rogotovskiy.toursight.service;

import com.opencsv.bean.CsvToBean;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.toursight.dto.create.CreateTourDto;
import ru.rogotovskiy.toursight.dto.update.UpdateTourDto;
import ru.rogotovskiy.toursight.entity.Language;
import ru.rogotovskiy.toursight.entity.TourTranslation;
import ru.rogotovskiy.toursight.mapper.TourTranslationMapper;
import ru.rogotovskiy.toursight.repository.TourTranslationRepository;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourTranslationService {

    private final TourTranslationRepository repository;
    private final LanguageService languageService;
    private final TourTranslationMapper mapper;
    private final TranslationService translationService;

    public TourTranslation getTranslation(Integer tourId, String languageCode) {
        return repository.findByTourIdAndLanguage_Code(tourId, languageCode).orElseThrow(
                () -> new NoSuchElementException("Не найден")
        );
    }

    public void createTourTranslation(CreateTourDto dto, Integer tourId) {
        List<Language> languages = languageService.getAll();
        for (Language language: languages) {
            createTourTranslation(dto, tourId, language);
        }
    }

    public void createTourTranslation(CreateTourDto dto, Integer tourId, Language language) {
        if (language.getCode().equalsIgnoreCase("ru")) {
            repository.save(mapper.toEntity(dto, tourId, language));
        } else {
            repository.save(translateTour(dto, tourId, language));
        }
    }

    private TourTranslation translateTour(CreateTourDto dto, Integer tourId, Language language) {
        try {
            return new TourTranslation(
                    null,
                    tourId,
                    language,
                    translationService.translate(dto.getName(), language.getCode()),
                    translationService.translate(dto.getDescription(), language.getCode()),
                    translationService.translate(dto.getCity(), language.getCode()),
                    translationService.translate(dto.getCategory(), language.getCode()),
                    translationService.translate(dto.getType(), language.getCode())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTourTranslation(UpdateTourDto dto, Integer tourId) {
        List<TourTranslation> translations = repository.findByTourId(tourId);
        if (dto.name() != null && !dto.name().isEmpty()) {
            for (TourTranslation translation : translations) {
                try {
                    translation.setName(translationService.translate(dto.name(), translation.getLanguage().getCode()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (dto.description() != null && !dto.description().isEmpty()) {
            for (TourTranslation translation : translations) {
                try {
                    translation.setDescription(translationService.translate(dto.description(), translation.getLanguage().getCode()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (dto.city() != null && !dto.city().isEmpty()) {
            for (TourTranslation translation : translations) {
                try {
                    translation.setCity(translationService.translate(dto.city(), translation.getLanguage().getCode()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (dto.category() != null && !dto.category().isEmpty()) {
            for (TourTranslation translation : translations) {
                try {
                    translation.setCity(translationService.translate(dto.category(), translation.getLanguage().getCode()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (dto.type() != null && !dto.type().isEmpty()) {
            for (TourTranslation translation : translations) {
                try {
                    translation.setCity(translationService.translate(dto.type(), translation.getLanguage().getCode()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        repository.saveAll(translations);
    }

    public Set<Integer> findTourIdsByQueryAndLanguage(String likeQuery, String language) {
        return repository.findByNameOrCityLikeIgnoreCaseAndLanguage(likeQuery, language)
                .stream()
                .map(TourTranslation::getTourId)
                .collect(Collectors.toSet());
    }

    public List<TourTranslation> findByLanguage(String language) {
        return repository.findByLanguage_Code(language);
    }
}
