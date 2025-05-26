package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto

data class SightDTO(
    var id: Long,
    var name: String,
    var description: String,
    var city: String,
    var imagePath: String,
    var latitude: Float,
    var longitude: Float,
    var rating: Float
)
