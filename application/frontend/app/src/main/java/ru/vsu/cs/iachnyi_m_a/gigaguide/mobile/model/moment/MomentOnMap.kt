package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.moment

data class MomentOnMap(
    var id: Long,
    var name: String,
    var latitude: Double,
    var longitude: Double,
    var audioLink: String,
    var imageLink: String
)