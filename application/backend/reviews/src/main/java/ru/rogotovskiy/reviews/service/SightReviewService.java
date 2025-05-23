package ru.rogotovskiy.reviews.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.reviews.dto.CreateSightReviewDto;
import ru.rogotovskiy.reviews.dto.SightReviewsDto;
import ru.rogotovskiy.reviews.entity.SightReview;
import ru.rogotovskiy.reviews.mapper.SightReviewMapper;
import ru.rogotovskiy.reviews.repository.SightReviewRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SightReviewService {

    private final SightReviewRepository reviewRepository;
    private final SightReviewMapper mapper;

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
                () -> new NoSuchElementException("Отзыв с id = %d не найден".formatted(reviewId))
        );
        if (!review.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Пользователь не может удалить не свой отзыв");
        }
        reviewRepository.delete(review);
    }
}
