package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight

data class Sight(
    var id: Long,
    var name: String,
    var description: String,
    var momentNames: List<String>,
    var time: Int,
    var latitude: Double,
    var longitude: Double
)
