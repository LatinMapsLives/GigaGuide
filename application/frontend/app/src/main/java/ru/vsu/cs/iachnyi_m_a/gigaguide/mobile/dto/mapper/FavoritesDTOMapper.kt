package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper

import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.favorites.FavoritesDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.FavoritesList

class FavoritesDTOMapper: Mapper<FavoritesDTO, FavoritesList>{
    override fun map(value: FavoritesDTO): FavoritesList {
        return FavoritesList(sightIds = value.favoriteSights.map { f -> f.id }, tourIds = value.favoriteTours.map { f -> f.id })
    }
}