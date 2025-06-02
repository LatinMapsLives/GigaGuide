package ru.rogotovskiy.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.userservice.entity.SightTranslation;

import java.util.Optional;

@Repository
public interface SightTranslationRepository extends JpaRepository<SightTranslation, Integer> {
    Optional<SightTranslation> findBySightIdAndLanguage_Code(Integer sightId, String languageCode);
}
