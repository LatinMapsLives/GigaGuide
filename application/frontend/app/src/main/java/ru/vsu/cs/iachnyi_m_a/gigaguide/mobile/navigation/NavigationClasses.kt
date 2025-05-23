package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenObject
@Serializable
object MapScreenObject
@Serializable
object FavoriteScreenObject
@Serializable
object SettingsScreenObject
@Serializable
object LoginScreenObject
@Serializable
object RegisterScreenObject
@Serializable
data class SightPageScreenClass(
    val sightId: Long
)
@Serializable
data class ExploreSightScreenClass(
    val sightId: Long
)
@Serializable
data class SightReviewScreenClass(
    val sightId: Long
)
@Serializable
data class TourReviewScreenClass(
    val tourId: Long
)
@Serializable
object SearchScreenObject
@Serializable
data class TourPageScreenClass(
    val tourId: Long,
)
@Serializable
data class ExploreTourScreenClass(
    var tourId: Long
)
@Serializable
object ProfileScreenObject