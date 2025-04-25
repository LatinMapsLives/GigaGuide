package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.navigation

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