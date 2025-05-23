package ru.rogotovskiy.reviews.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.reviews.dto.create.CreateTourReviewDto;
import ru.rogotovskiy.reviews.dto.read.TourReviewsDto;
import ru.rogotovskiy.reviews.entity.TourReview;
import ru.rogotovskiy.reviews.exception.ReviewNotFoundException;
import ru.rogotovskiy.reviews.exception.ReviewPermissionException;
import ru.rogotovskiy.reviews.mapper.TourReviewMapper;
import ru.rogotovskiy.reviews.repository.TourReviewRepository;

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
                mapper.toDto(userReview),
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
}
