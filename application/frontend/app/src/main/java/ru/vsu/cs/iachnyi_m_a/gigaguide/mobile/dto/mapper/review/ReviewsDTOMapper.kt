package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper.review

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper.Mapper
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.review.ReviewsDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.review.ReviewSet

class ReviewsDTOMapper: Mapper<ReviewsDTO, ReviewSet> {
    override fun map(value: ReviewsDTO): ReviewSet {
        var res = ReviewSet(null, listOf())
        var mapper = ReviewDTOMapper()
        if(value.userReview != null){
            res.myReview = mapper.map(value.userReview!!)
        }
        res.otherReviews = value.otherReviews.map { mapper.map(it) }
        return res
    }
}