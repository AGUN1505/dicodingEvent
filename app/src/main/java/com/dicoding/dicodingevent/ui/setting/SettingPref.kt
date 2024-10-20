@file:Suppress("PrivatePropertyName")

package com.dicoding.dicodingevent.ui.setting

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPref private constructor(private val dataStore: DataStore<Preferences>) {

    private val THEME_KEY = booleanPreferencesKey("theme_setting")
    private val NOTIFICATION_KEY = booleanPreferencesKey("notification_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    fun getNotificationSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[NOTIFICATION_KEY] ?: false
        }
    }

    suspend fun saveNotificationSetting(isNotificationEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[NOTIFICATION_KEY] = isNotificationEnabled
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPref? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPref {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPref(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}
