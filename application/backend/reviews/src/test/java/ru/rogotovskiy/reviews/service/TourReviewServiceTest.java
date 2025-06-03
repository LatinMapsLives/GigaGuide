package ru.rogotovskiy.reviews.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.rogotovskiy.reviews.dto.create.CreateTourReviewDto;
import ru.rogotovskiy.reviews.dto.read.TourReviewsDto;
import ru.rogotovskiy.reviews.entity.Tour;
import ru.rogotovskiy.reviews.entity.TourReview;
import ru.rogotovskiy.reviews.exception.ReviewNotFoundException;
import ru.rogotovskiy.reviews.exception.ReviewPermissionException;
import ru.rogotovskiy.reviews.repository.TourRepository;
import ru.rogotovskiy.reviews.repository.TourReviewRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TourReviewServiceTest {

    @Mock
    private TourReviewRepository reviewRepository;

    @Mock
    private TourRepository tourRepository;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private TourReviewService reviewService;

    @Test
    void addReview_ShouldCreateNewReview() {
        // Arrange
        CreateTourReviewDto dto = new CreateTourReviewDto(5, "Amazing");
        when(tourRepository.findById(1)).thenReturn(Optional.of(new Tour()));

        // Act
        reviewService.addReview(1, 1, dto);

        // Assert
        verify(reviewRepository).save(any(TourReview.class));
    }

    @Test
    void deleteReview_ShouldDelete_WhenUserIsOwner() {
        // Arrange
        TourReview review = new TourReview(1, 1, 1, 5, "Great", LocalDateTime.now());
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        // Act
        reviewService.deleteReview(1, 1);

        // Assert
        verify(reviewRepository).delete(review);
    }

    @Test
    void updateTourRating_ShouldHandleNullRating() {
        // Arrange
        when(reviewRepository.getAverageRatingByTourId(1)).thenReturn(null);
        Tour tour = new Tour();
        when(tourRepository.findById(1)).thenReturn(Optional.of(tour));

        // Act
        reviewService.updateTourRating(1);

        // Assert
        assertEquals(BigDecimal.ZERO.setScale(1), tour.getRating());
    }
}