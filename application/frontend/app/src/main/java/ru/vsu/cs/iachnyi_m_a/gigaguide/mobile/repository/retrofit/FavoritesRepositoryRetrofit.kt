package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit

import android.util.Log
import retrofit2.Response
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.FavoritesAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.favorites.FavoritesDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper.FavoritesDTOtoFavoritesListMapper
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.FavoritesList
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.FavoritesRepository

class FavoritesRepositoryRetrofit(private val favoritesAPI: FavoritesAPI): FavoritesRepository{

    override suspend fun getFavoriteSights(token: String): FavoritesList? {
        var response: Response<FavoritesDTO> = favoritesAPI.getAllFavoriteSights("Bearer " + token).execute();
        return if (response.isSuccessful) FavoritesDTOtoFavoritesListMapper().map(response.body()!!) else null
    }

    override suspend fun addToFavorites(
        token: String,
        sightId: Long
    ): Boolean {
        var response: Response<String> = favoritesAPI.addSightToFavorites("Bearer $token", sightId).execute();
        return response.isSuccessful;
    }

    override suspend fun deleteFromFavorites(
        token: String,
        sightId: Long
    ): Boolean {
        var response: Response<String> = favoritesAPI.deleteSightFromFavorites("Bearer $token", sightId).execute();
        return response.isSuccessful;
    }
}