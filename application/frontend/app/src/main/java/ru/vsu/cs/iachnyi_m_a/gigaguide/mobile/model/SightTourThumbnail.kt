package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model

import android.media.Image

data class SightTourThumbnail(
    var sightId: Long,
    var name: String,
    var rating: Float,
    var proximity: Float
)
