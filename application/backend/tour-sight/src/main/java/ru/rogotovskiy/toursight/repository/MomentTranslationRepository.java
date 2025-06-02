package ru.rogotovskiy.toursight.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.toursight.entity.MomentTranslation;

import java.util.List;
import java.util.Optional;

@Repository
public interface MomentTranslationRepository extends CrudRepository<MomentTranslation, Integer> {
    Optional<MomentTranslation> findByMomentIdAndLanguage_Code(Integer momentId, String languageCode);
    List<MomentTranslation> findByMomentId(Integer momentId);
}
