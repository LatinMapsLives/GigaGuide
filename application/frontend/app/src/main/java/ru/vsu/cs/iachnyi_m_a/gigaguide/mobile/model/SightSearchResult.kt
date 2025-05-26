package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model

data class SightSearchResult(
    val id: Int,
    val name: String,
    var rating: Float,
    var latitude: Float,
    var longitude: Float,
    var imageLink: String
)
