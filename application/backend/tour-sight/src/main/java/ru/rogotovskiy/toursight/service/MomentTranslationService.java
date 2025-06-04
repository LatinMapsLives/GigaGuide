package ru.rogotovskiy.toursight.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.toursight.dto.create.CreateMomentDto;
import ru.rogotovskiy.toursight.dto.update.UpdateMomentDto;
import ru.rogotovskiy.toursight.entity.Language;
import ru.rogotovskiy.toursight.entity.MomentTranslation;
import ru.rogotovskiy.toursight.mapper.MomentTranslationMapper;
import ru.rogotovskiy.toursight.repository.MomentTranslationRepository;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MomentTranslationService {

    private final MomentTranslationRepository repository;
    private final LanguageService languageService;
    private final MomentTranslationMapper mapper;
    private final TranslationService translationService;

    public MomentTranslation getMomentTranslation(Integer momentId, String languageCode) {
        return repository.findByMomentIdAndLanguage_Code(momentId, languageCode).orElseThrow(
                () -> new NoSuchElementException("Не найден")
        );
    }

    public void createMomentTranslation(CreateMomentDto dto, Integer momentId) {
        List<Language> languages = languageService.getAll();
        for (Language language : languages) {
            createMomentTranslation(dto, momentId, language);
        }
    }

    public void createMomentTranslation(CreateMomentDto dto, Integer momentId, Language language) {
        if (language.getCode().equalsIgnoreCase("ru")) {
            repository.save(mapper.toEntity(dto, momentId, language));
        } else {
            repository.save(translateMoment(dto, momentId, language));
        }
    }

    public MomentTranslation translateMoment(CreateMomentDto dto, Integer momentId, Language language) {
        try {
            return new MomentTranslation(
                    null,
                    momentId,
                    language,
                    translationService.translate(dto.name(), language.getCode()),
                    translationService.translate(dto.content(), language.getCode())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateMomentTranslation(UpdateMomentDto dto, Integer momentId) {
        List<MomentTranslation> translations = repository.findByMomentId(momentId);
        if (dto.name() != null && !dto.name().isEmpty()) {
            for (MomentTranslation translation : translations) {
                try {
                    translation.setName(translationService.translate(dto.name(), translation.getLanguage().getCode()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (dto.content() != null && !dto.content().isEmpty()) {
            for (MomentTranslation translation : translations) {
                try {
                    translation.setContent(translationService.translate(dto.content(), translation.getLanguage().getCode()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        repository.saveAll(translations);
    }
}
