package ru.rogotovskiy.reviews.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.reviews.entity.SightReview;

import java.util.List;

@Repository
public interface SightReviewRepository extends CrudRepository<SightReview, Integer> {

    List<SightReview> findAllBySightId(Integer sightId);
    SightReview findBySightIdAndUserId(Integer sightId, Integer userId);
}
