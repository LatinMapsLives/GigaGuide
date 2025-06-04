package ru.rogotovskiy.toursight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.toursight.entity.SightTranslation;
import ru.rogotovskiy.toursight.entity.TourTranslation;

import java.util.List;
import java.util.Optional;

@Repository
public interface SightTranslationRepository extends JpaRepository<SightTranslation, Integer> {
    Optional<SightTranslation> findBySightIdAndLanguage_Code(Integer sightId, String languageCode);
    List<SightTranslation> findByNameContainingIgnoreCaseAndLanguage_Code(String name, String languageCode);
    List<SightTranslation> findBySightId(Integer sightId);
    @Query("SELECT s FROM SightTranslation s WHERE s.language.code = :languageCode AND " +
            "(LOWER(s.name) LIKE :query OR LOWER(s.city) LIKE :query)")
    List<SightTranslation> findByNameOrCityLikeIgnoreCaseAndLanguage(@Param("query") String query,
                                                                     @Param("languageCode") String languageCode);
}
