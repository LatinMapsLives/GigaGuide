package ru.rogotovskiy.reviews.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rogotovskiy.reviews.entity.TourReview;

import java.util.List;

@Repository
public interface TourReviewRepository extends CrudRepository<TourReview, Integer> {

    List<TourReview> findAllByTourId(Integer tourId);
    TourReview findByTourIdAndUserId(Integer tourId, Integer userId);
}
