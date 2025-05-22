package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model

data class TourInfo(
    var id: Long,
    var name: String,
    var description: String,
    var imageLink: String,
    var durationMinutes: Int,
    var distanceKm: Float,
    var category: String,
    var type: String,
    var rating: Float
)
