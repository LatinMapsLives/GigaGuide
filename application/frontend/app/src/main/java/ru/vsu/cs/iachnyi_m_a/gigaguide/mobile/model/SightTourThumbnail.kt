package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model

import android.media.Image

data class SightTourThumbnail(
    var name: String,
    var imageLink: String,
    var rating: Float,
    var proximity: Float
)
