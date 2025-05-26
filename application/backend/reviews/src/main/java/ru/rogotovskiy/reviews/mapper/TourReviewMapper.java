package ru.rogotovskiy.reviews.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rogotovskiy.reviews.dto.read.TourReviewDto;
import ru.rogotovskiy.reviews.entity.TourReview;
import ru.rogotovskiy.reviews.service.UserService;

@Component
@RequiredArgsConstructor
public class TourReviewMapper {

    private final UserService userService;

    public TourReviewDto toDto(TourReview review) {
        return new TourReviewDto(
                review.getId(),
                userService.getUsername(review.getUserId()),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
    }
}
