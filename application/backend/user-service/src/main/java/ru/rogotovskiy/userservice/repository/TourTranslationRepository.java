package ru.rogotovskiy.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.userservice.entity.TourTranslation;

import java.util.Optional;

@Repository
public interface TourTranslationRepository extends JpaRepository<TourTranslation, Integer> {
    Optional<TourTranslation> findByTourIdAndLanguage_Code(Integer tourId, String languageCode);
}
