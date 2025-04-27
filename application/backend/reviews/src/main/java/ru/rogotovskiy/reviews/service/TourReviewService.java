package ru.rogotovskiy.reviews.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.reviews.dto.CreateTourReviewDto;
import ru.rogotovskiy.reviews.dto.TourReviewDto;
import ru.rogotovskiy.reviews.entity.TourReview;
import ru.rogotovskiy.reviews.mapper.TourReviewMapper;
import ru.rogotovskiy.reviews.repository.TourReviewRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TourReviewService {

    private final TourReviewRepository reviewRepository;
    private final TourReviewMapper mapper;
    private final UserService userService;


    public List<TourReviewDto> getAll(Integer tourId) {
        return reviewRepository.findAllByTourId(tourId).stream()
                .map(mapper::toDto)
                .toList();
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
                () -> new NoSuchElementException("Отзыв с id = %d не найден".formatted(reviewId))
        );
        if (!tourReview.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Пользователь не может удалить не свой отзыв");
        }

        reviewRepository.delete(tourReview);
    }
}
