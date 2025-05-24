package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.retrofit

import retrofit2.Response
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.api.FavoritesAPI
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.favorites.FavoritesDTO
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto.mapper.FavoritesDTOMapper
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.FavoritesList
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.repository.FavoritesRepository

class FavoritesRepositoryRetrofit(private val favoritesAPI: FavoritesAPI): FavoritesRepository{

    override suspend fun getFavorites(token: String): FavoritesList? {
        var response: Response<FavoritesDTO> = favoritesAPI.getAllFavoriteSights("Bearer " + token).execute();
        return if (response.isSuccessful) FavoritesDTOMapper().map(response.body()!!) else null
    }

    override suspend fun addSightToFavorites(
        token: String,
        sightId: Long
    ): Boolean {
        var response: Response<String> = favoritesAPI.addSightToFavorites("Bearer $token", sightId).execute();
        return response.isSuccessful;
    }

    override suspend fun deleteSightFromFavorites(
        token: String,
        sightId: Long
    ): Boolean {
        var response: Response<String> = favoritesAPI.deleteSightFromFavorites("Bearer $token", sightId).execute();
        return response.isSuccessful;
    }

    override suspend fun addTourToFavorites(
        token: String,
        sightId: Long
    ): Boolean? {
        var response: Response<String> = favoritesAPI.addTourToFavorites("Bearer $token", sightId).execute();
        return response.isSuccessful;
    }

    override suspend fun deleteTourFromFavorites(
        token: String,
        sightId: Long
    ): Boolean? {
        var response: Response<String> = favoritesAPI.deleteTourFromFavorites("Bearer $token", sightId).execute();
        return response.isSuccessful;
    }
}