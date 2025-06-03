package ru.rogotovskiy.reviews.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.rogotovskiy.reviews.dto.create.CreateSightReviewDto;
import ru.rogotovskiy.reviews.dto.read.SightReviewDto;
import ru.rogotovskiy.reviews.dto.read.SightReviewsDto;
import ru.rogotovskiy.reviews.entity.Sight;
import ru.rogotovskiy.reviews.entity.SightReview;
import ru.rogotovskiy.reviews.exception.ReviewNotFoundException;
import ru.rogotovskiy.reviews.exception.ReviewPermissionException;
import ru.rogotovskiy.reviews.mapper.SightReviewMapper;
import ru.rogotovskiy.reviews.repository.SightRepository;
import ru.rogotovskiy.reviews.repository.SightReviewRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SightReviewServiceTest {

    @Mock
    private SightReviewRepository reviewRepository;

    @Mock
    private SightRepository sightRepository;

    @Mock
    private MessageSource messageSource;

    @Mock
    private SightReviewMapper sightReviewMapper;

    @InjectMocks
    private SightReviewService reviewService;

    @Test
    void getAll_ShouldReturnAllReviews_WhenUserIdIsNull() {
        // Arrange
        SightReview review1 = new SightReview(1, 1, 1, 5, "Great", LocalDateTime.now());
        SightReview review2 = new SightReview(2, 2, 1, 4, "Good", LocalDateTime.now());
        when(reviewRepository.findAllBySightId(1)).thenReturn(List.of(review1, review2));
        when(sightReviewMapper.toDto(review1)).thenReturn(new SightReviewDto(
                review1.getUserId(),
                "test",
                review1.getRating(),
                review1.getComment(),
                review1.getCreatedAt()));
        when(sightReviewMapper.toDto(review2)).thenReturn(new SightReviewDto(
                review2.getUserId(),
                "test",
                review2.getRating(),
                review2.getComment(),
                review2.getCreatedAt()));

        // Act
        SightReviewsDto result = reviewService.getAll(1, null);

        // Assert
        assertNull(result.userReview());
        assertEquals(2, result.otherReviews().size());
    }

    @Test
    void addReview_ShouldSaveReviewAndUpdateRating() {
        // Arrange
        CreateSightReviewDto dto = new CreateSightReviewDto(5, "Excellent");
        when(sightRepository.findById(1)).thenReturn(Optional.of(new Sight()));

        // Act
        reviewService.addReview(1, 1, dto);

        // Assert
        verify(reviewRepository).save(any(SightReview.class));
        verify(sightRepository).save(any(Sight.class));
    }

    @Test
    void deleteReview_ShouldThrowPermissionException_WhenUserNotOwner() {
        // Arrange
        SightReview review = new SightReview(1, 2, 1, 5, "Great", LocalDateTime.now());
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("Error");

        // Act & Assert
        assertThrows(ReviewPermissionException.class, () ->
                reviewService.deleteReview(1, 1));
    }

    @Test
    void updateSightRating_ShouldCalculateAverageCorrectly() {
        // Arrange
        when(reviewRepository.getAverageRatingBySightId(1)).thenReturn(BigDecimal.valueOf(4.5));
        Sight sight = new Sight();
        when(sightRepository.findById(1)).thenReturn(Optional.of(sight));

        // Act
        reviewService.updateSightRating(1);

        // Assert
        assertEquals(BigDecimal.valueOf(4.5).setScale(1, BigDecimal.ROUND_HALF_UP), sight.getRating());
        verify(sightRepository).save(sight);
    }
}