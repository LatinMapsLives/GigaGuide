package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("auth")
private val JWT_SETTING_KEY = "JWT"

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
}