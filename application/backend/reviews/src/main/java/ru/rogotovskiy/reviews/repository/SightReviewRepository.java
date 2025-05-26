package ru.rogotovskiy.reviews.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.reviews.entity.SightReview;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SightReviewRepository extends CrudRepository<SightReview, Integer> {

    List<SightReview> findAllBySightId(Integer sightId);
    SightReview findBySightIdAndUserId(Integer sightId, Integer userId);
    @Query("SELECT AVG(r.rating) FROM SightReview r WHERE r.sightId = :sightId")
    BigDecimal getAverageRatingBySightId(@Param("sightId") Integer sightId);
}
