package ru.rogotovskiy.reviews.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.rogotovskiy.reviews.dto.read.SightReviewDto;
import ru.rogotovskiy.reviews.entity.SightReview;
import ru.rogotovskiy.reviews.service.UserService;

@Component
@RequiredArgsConstructor
public class SightReviewMapper {

    private final UserService userService;

    public SightReviewDto toDto(SightReview sightReview) {
        return new SightReviewDto(
                sightReview.getId(),
                userService.getUsername(sightReview.getUserId()),
                sightReview.getRating(),
                sightReview.getComment(),
                sightReview.getCreatedAt()
        );
    }
}
