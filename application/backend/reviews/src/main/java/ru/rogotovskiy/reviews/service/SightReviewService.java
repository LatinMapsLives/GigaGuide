package ru.rogotovskiy.reviews.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.reviews.dto.create.CreateSightReviewDto;
import ru.rogotovskiy.reviews.dto.read.SightReviewsDto;
import ru.rogotovskiy.reviews.entity.SightReview;
import ru.rogotovskiy.reviews.exception.ReviewNotFoundException;
import ru.rogotovskiy.reviews.exception.ReviewPermissionException;
import ru.rogotovskiy.reviews.mapper.SightReviewMapper;
import ru.rogotovskiy.reviews.repository.SightReviewRepository;

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
}
