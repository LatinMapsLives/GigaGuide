package ru.rogotovskiy.reviews.mapper;

import org.springframework.stereotype.Component;
import ru.rogotovskiy.reviews.dto.SightReviewDto;
import ru.rogotovskiy.reviews.entity.SightReview;

@Component
public class SightReviewMapper {

    public SightReviewDto toDto(SightReview sightReview) {
        return new SightReviewDto(
                sightReview.getId(),
                sightReview.getRating(),
                sightReview.getComment(),
                sightReview.getCreatedAt()
        );
    }
}
