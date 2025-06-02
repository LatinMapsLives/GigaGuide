package ru.rogotovskiy.toursight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.toursight.entity.SightTranslation;

import java.util.List;
import java.util.Optional;

@Repository
public interface SightTranslationRepository extends JpaRepository<SightTranslation, Integer> {
    Optional<SightTranslation> findBySightIdAndLanguage_Code(Integer sightId, String languageCode);
    List<SightTranslation> findByNameContainingIgnoreCaseAndLanguage_Code(String name, String languageCode);
    List<SightTranslation> findBySightId(Integer sightId);
}
