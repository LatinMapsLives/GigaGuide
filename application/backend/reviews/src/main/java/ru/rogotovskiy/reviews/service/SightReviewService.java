package ru.rogotovskiy.reviews.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.reviews.dto.create.CreateSightReviewDto;
import ru.rogotovskiy.reviews.dto.read.SightReviewsDto;
import ru.rogotovskiy.reviews.entity.Sight;
import ru.rogotovskiy.reviews.entity.SightReview;
import ru.rogotovskiy.reviews.exception.ReviewNotFoundException;
import ru.rogotovskiy.reviews.exception.ReviewPermissionException;
import ru.rogotovskiy.reviews.mapper.SightReviewMapper;
import ru.rogotovskiy.reviews.repository.SightRepository;
import ru.rogotovskiy.reviews.repository.SightReviewRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SightReviewService {

    private final SightReviewRepository reviewRepository;
    private final SightReviewMapper mapper;
    private final MessageSource messageSource;
    private final SightRepository sightRepository;

    public SightReviewsDto getAll(Integer sightId, String userId) {
        List<SightReview> allReviews = reviewRepository.findAllBySightId(sightId);

        if (userId == null) {
            return new SightReviewsDto(
                    null,
                    allReviews.stream()
                            .map(mapper::toDto)
                            .toList()
            );
        }

        SightReview userReview = reviewRepository.findBySightIdAndUserId(sightId, Integer.parseInt(userId));
        allReviews.remove(userReview);
        return new SightReviewsDto(
                userReview != null ? mapper.toDto(userReview) : null,
                allReviews.stream()
                        .map(mapper::toDto)
                        .toList()
        );

    }

    public void addReview(Integer userId, Integer sightId, CreateSightReviewDto dto) {
        reviewRepository.save(new SightReview(
                null,
                userId,
                sightId,
                dto.rating(),
                dto.comment(),
                LocalDateTime.now()
        ));
        updateSightRating(sightId);
    }

    public void deleteReview(Integer userId, Integer reviewId) {
        SightReview review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException(
                        "REVIEW_NOT_FOUND",
                        messageSource.getMessage("reviews.errors.review.not_found", null, Locale.ROOT)
                )
        );
        if (!review.getUserId().equals(userId)) {
            throw new ReviewPermissionException(
                    "REVIEW_DELETE_PERMISSION",
                    messageSource.getMessage("reviews.errors.review.delete_permission", null, Locale.ROOT)
            );
        }
        reviewRepository.delete(review);
    }

    public void updateSightRating(Integer sightId) {
        BigDecimal avgRating = reviewRepository.getAverageRatingBySightId(sightId);
        if (avgRating == null) avgRating = BigDecimal.ZERO;
        avgRating = avgRating.setScale(1, RoundingMode.HALF_UP);
        Sight sight = sightRepository.findById(sightId).orElseThrow(
                () -> new NoSuchElementException("Достопримечательность не найдена")
        );
        sight.setRating(avgRating);
        sightRepository.save(sight);
    }
}
