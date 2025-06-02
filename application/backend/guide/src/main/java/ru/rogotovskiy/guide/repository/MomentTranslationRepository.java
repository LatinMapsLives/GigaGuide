package ru.rogotovskiy.guide.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.guide.entity.MomentTranslation;

import java.util.Optional;

@Repository
public interface MomentTranslationRepository extends CrudRepository<MomentTranslation, Integer> {
    Optional<MomentTranslation> findByMomentIdAndLanguage_Code(Integer momentId, String languageCode);
}

