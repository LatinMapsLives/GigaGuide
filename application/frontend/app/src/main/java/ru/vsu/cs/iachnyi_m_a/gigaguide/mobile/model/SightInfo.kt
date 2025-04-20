package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model

data class SightInfo (
    var id: Long,
    var name: String,
    var description: String,
    var momentNames: List<String>,
    var rating: Float
)