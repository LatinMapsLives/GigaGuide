package ru.rogotovskiy.toursight.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.toursight.entity.TourTranslation;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourTranslationRepository extends CrudRepository<TourTranslation, Integer> {
    Optional<TourTranslation> findByTourIdAndLanguage_Code(Integer tourId, String languageCode);
    List<TourTranslation> findByTourId(Integer tourId);
    @Query("SELECT t FROM TourTranslation t WHERE t.language.code = :languageCode AND " +
            "(LOWER(t.name) LIKE :query OR LOWER(t.city) LIKE :query)")
    List<TourTranslation> findByNameOrCityLikeIgnoreCaseAndLanguage(@Param("query") String query, @Param("languageCode") String languageCode);
    List<TourTranslation> findByLanguage_Code(String languageCode);
}
