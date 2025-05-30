package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper.review

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper.Mapper
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.review.ReviewDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.review.Review
import java.time.LocalDate
import java.time.LocalDateTime

class ReviewDTOMapper: Mapper<ReviewDTO, Review> {
    override fun map(value: ReviewDTO): Review {
        return Review(id = value.id, rating = value.rating, text = value.comment, userName = value.username, date = LocalDateTime.parse(value.createdAt))
    }
}