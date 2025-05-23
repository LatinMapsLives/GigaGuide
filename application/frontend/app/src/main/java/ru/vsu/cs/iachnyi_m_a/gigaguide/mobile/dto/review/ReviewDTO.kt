package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.review

import java.util.Date

data class ReviewDTO(
    var id: Int,
    var rating: Int,
    var comment: String,
    var createdAt: Date
)
