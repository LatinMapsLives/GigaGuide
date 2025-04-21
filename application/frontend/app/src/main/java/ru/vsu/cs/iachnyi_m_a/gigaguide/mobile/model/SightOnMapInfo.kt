package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model

data class SightOnMapInfo(
    var id: Long,
    var name: String,
    var latitude: Double,
    var longitude: Double
) {
    constructor(sight: Sight) : this(
        id = sight.id,
        name = sight.name,
        latitude = sight.latitude,
        longitude = sight.longitude
    )
}
