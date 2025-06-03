package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight

data class SightTourThumbnail(
    var isTour: Boolean,
    var sightId: Long,
    var name: String,
    var rating: Float,
    var proximity: Float,
    var imageLink: String
)
