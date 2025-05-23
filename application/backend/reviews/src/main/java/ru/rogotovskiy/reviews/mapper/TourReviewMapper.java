package ru.rogotovskiy.reviews.mapper;

import org.springframework.stereotype.Component;
import ru.rogotovskiy.reviews.dto.read.TourReviewDto;
import ru.rogotovskiy.reviews.entity.TourReview;

@Component
public class TourReviewMapper {

    public TourReviewDto toDto(TourReview review) {
        return new TourReviewDto(
                review.getId(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
    }
}
