package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.sight


data class SightInfo (
    var id: Long,
    var name: String,
    var description: String,
    var time: Int,
    var imageLink: String,
    var city: String
)