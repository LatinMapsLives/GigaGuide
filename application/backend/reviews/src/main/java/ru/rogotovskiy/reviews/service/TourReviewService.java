package ru.rogotovskiy.reviews.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.reviews.dto.create.CreateTourReviewDto;
import ru.rogotovskiy.reviews.dto.read.TourReviewsDto;
import ru.rogotovskiy.reviews.entity.Sight;
import ru.rogotovskiy.reviews.entity.Tour;
import ru.rogotovskiy.reviews.entity.TourReview;
import ru.rogotovskiy.reviews.exception.ReviewNotFoundException;
import ru.rogotovskiy.reviews.exception.ReviewPermissionException;
import ru.rogotovskiy.reviews.mapper.TourReviewMapper;
import ru.rogotovskiy.reviews.repository.TourRepository;
import ru.rogotovskiy.reviews.repository.TourReviewRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TourReviewService {

    private final TourReviewRepository reviewRepository;
    private final TourReviewMapper mapper;
    private final MessageSource messageSource;
    private final TourRepository tourRepository;


    public TourReviewsDto getAll(Integer tourId, String userId) {
        List<TourReview> allReviews = reviewRepository.findAllByTourId(tourId);

        if (userId == null) {
            return new TourReviewsDto(
                    null,
                    allReviews.stream()
                            .map(mapper::toDto)
                            .toList()
            );
        }

        TourReview userReview = reviewRepository.findByTourIdAndUserId(tourId, Integer.parseInt(userId));
        allReviews.remove(userReview);
        return new TourReviewsDto(
                userReview != null ? mapper.toDto(userReview) : null,
                allReviews.stream()
                        .map(mapper::toDto)
                        .toList()
        );
    }


    public void addReview(Integer userId, Integer tourId, CreateTourReviewDto dto) {
        reviewRepository.save(new TourReview(
                null,
                userId,
                tourId,
                dto.rating(),
                dto.comment(),
                LocalDateTime.now())
        );
    }

    public void deleteReview(Integer userId, Integer reviewId) {
        TourReview tourReview = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException(
                        "REVIEW_NOT_FOUND",
                        messageSource.getMessage("reviews.errors.review.not_found", null, Locale.ROOT)
                )
        );
        if (!tourReview.getUserId().equals(userId)) {
            throw new ReviewPermissionException(
                    "REVIEW_DELETE_PERMISSION",
                    messageSource.getMessage("reviews.errors.review.delete_permission", null, Locale.ROOT)
            );
        }

        reviewRepository.delete(tourReview);
    }

    public void updateTourRating(Integer tourId) {
        BigDecimal avgRating = reviewRepository.getAverageRatingByTourId(tourId);
        if (avgRating == null) avgRating = BigDecimal.ZERO;
        avgRating = avgRating.setScale(1, RoundingMode.HALF_UP);
        Tour tour = tourRepository.findById(tourId).orElseThrow(
                () -> new NoSuchElementException("Тур не найден")
        );
        tour.setRating(avgRating);
        tourRepository.save(tour);
    }
}
