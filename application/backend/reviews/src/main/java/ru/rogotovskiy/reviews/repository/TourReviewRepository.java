package ru.rogotovskiy.reviews.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.reviews.entity.TourReview;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TourReviewRepository extends CrudRepository<TourReview, Integer> {

    List<TourReview> findAllByTourId(Integer tourId);
    TourReview findByTourIdAndUserId(Integer tourId, Integer userId);
    @Query("SELECT AVG(r.rating) FROM TourReview r WHERE r.tourId = :tourId")
    BigDecimal getAverageRatingByTourId(@Param("tourId") Integer tourId);
}
