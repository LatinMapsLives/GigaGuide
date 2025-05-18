package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.favorites

data class FavoritesDTO(
    var favoriteSights: List<FavoriteSightDTO>,
    var favoriteTours: List<FavoriteTourDTO>
)
