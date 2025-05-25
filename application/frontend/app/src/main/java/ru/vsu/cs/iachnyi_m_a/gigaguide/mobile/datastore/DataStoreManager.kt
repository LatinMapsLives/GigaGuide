package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.MapPoint
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.util.ThemeSettings

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("auth")
private val JWT_SETTING_KEY = "JWT"
private val LOCATION_LAT_KEY = "LOCATION_LAT"
private val LOCATION_LONG_KEY = "LOCATION_LONG"
private val THEME_KEY = "THEME"
private val LANGUAGE_KEY = "LANGUAGE"


class DataStoreManager(val context: Context) {

    suspend fun getCurrentLanguage(): String {
        var prefs = context.dataStore.data.first()
        var str = prefs[stringPreferencesKey(LANGUAGE_KEY)]
        return str?: "en"
    }

    suspend fun setLanguage(language: String) {
        context.dataStore.edit { pref ->
            pref[stringPreferencesKey(LANGUAGE_KEY)] = language
        }
    }

    suspend fun getThemeSettings(): ThemeSettings {
        var prefs = context.dataStore.data.first()
        var str = prefs[stringPreferencesKey(THEME_KEY)]
        return ThemeSettings.valueOf(str?: ThemeSettings.AS_DEVICE.name)
    }

    suspend fun setThemeSettings(settings: ThemeSettings){
        context.dataStore.edit { pref ->
            pref[stringPreferencesKey(THEME_KEY)] = settings.name
        }
    }

    suspend fun saveJWT(token: String) {
        context.dataStore.edit { pref ->
            pref[stringPreferencesKey(JWT_SETTING_KEY)] = token
        }
    }

    suspend fun getJWT(): String? {
        var prefs = context.dataStore.data.first()
        return prefs[stringPreferencesKey(JWT_SETTING_KEY)]
    }

    suspend fun deleteJWT() {
        context.dataStore.edit { pref ->
            pref.remove(stringPreferencesKey(JWT_SETTING_KEY))
        }
    }

    suspend fun saveLastLocation(point: MapPoint) {
        context.dataStore.edit { pref ->
            pref[doublePreferencesKey(LOCATION_LAT_KEY)] = point.latitude
            pref[doublePreferencesKey(LOCATION_LONG_KEY)] = point.longitude
        }
    }

    suspend fun getLastLocation(): MapPoint {
        var prefs = context.dataStore.data.first()
        var lat = prefs[doublePreferencesKey(LOCATION_LAT_KEY)]
        var long = prefs[doublePreferencesKey(LOCATION_LONG_KEY)]
        if (lat == null || long == null) {
            return MapPoint(51.665859, 39.211282)
        } else {
            return MapPoint(lat, long)
        }
    }
}