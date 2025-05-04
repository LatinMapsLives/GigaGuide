package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model

import java.util.Date

data class Review(
    var userName: String,
    var rating: Int,
    var text: String,
    var date: Date
)
