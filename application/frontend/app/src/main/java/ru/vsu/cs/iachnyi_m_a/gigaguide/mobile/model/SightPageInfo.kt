package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model

data class SightPageInfo (
    var id: Long,
    var name: String,
    var description: String,
    var momentNames: List<String>,
    var time: Int,
) {
    constructor(sight: Sight) : this(
        id = sight.id,
        name = sight.name,
        description = sight.description,
        momentNames = sight.momentNames,
        time = sight.time
    )
}