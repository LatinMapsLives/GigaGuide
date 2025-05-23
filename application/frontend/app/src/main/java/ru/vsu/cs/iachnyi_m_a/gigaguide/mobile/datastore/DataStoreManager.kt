package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import org.osmdroid.util.GeoPoint
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.model.MapPoint

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("auth")
private val JWT_SETTING_KEY = "JWT"
private val LOCATION_LAT_KEY = "LOCATION_LAT"
private val LOCATION_LONG_KEY = "LOCATION_LONG"


class DataStoreManager(val context: Context) {


    suspend fun saveJWT(token: String){
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
        if(lat == null || long == null){
            return MapPoint(51.665859, 39.211282)
        } else {
            return MapPoint(lat, long)
        }
    }
}