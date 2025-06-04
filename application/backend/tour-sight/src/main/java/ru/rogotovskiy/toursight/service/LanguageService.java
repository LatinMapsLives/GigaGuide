package ru.rogotovskiy.toursight.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.toursight.entity.Language;
import ru.rogotovskiy.toursight.repository.LanguageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository repository;

    public List<Language> getAll() {
        return repository.findAll();
    }
}
