package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper.review

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper.Mapper
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.review.ReviewDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.review.Review

class ReviewDTOMapper: Mapper<ReviewDTO, Review> {
    override fun map(value: ReviewDTO): Review {
        return Review(id = value.id, rating = value.rating, text = value.comment, userName = "", date = value.createdAt)
    }
}