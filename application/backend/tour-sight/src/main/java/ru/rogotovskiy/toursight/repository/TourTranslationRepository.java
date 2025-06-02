package ru.rogotovskiy.toursight.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.toursight.entity.TourTranslation;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourTranslationRepository extends CrudRepository<TourTranslation, Integer> {
    Optional<TourTranslation> findByTourIdAndLanguage_Code(Integer tourId, String languageCode);
    List<TourTranslation> findByTourId(Integer tourId);
}
