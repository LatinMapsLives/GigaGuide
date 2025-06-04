package ru.rogotovskiy.map.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.map.entity.TourTranslation;

import java.util.Optional;

@Repository
public interface TourTranslationRepository extends CrudRepository<TourTranslation, Integer> {
    Optional<TourTranslation> findByTourIdAndLanguage_Code(Integer tourId, String languageCode);
}
