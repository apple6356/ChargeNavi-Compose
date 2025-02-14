package com.seo.sesac.firestore.datasource.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES_NAME = "user_preferences"

private object PreferencesKeys {
    val USER_ID_KEY = stringPreferencesKey("user_id")
    val USER_NICKNAME_KEY = stringPreferencesKey("user_nickname")
}

private val Context.userDatastore by preferencesDataStore(name = USER_PREFERENCES_NAME)

class LocalUserDatasource(private val context: Context) {

    suspend fun saveUserInfo(userId: String, nickname: String) =
        context.userDatastore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID_KEY] = userId
            preferences[PreferencesKeys.USER_NICKNAME_KEY] = nickname
        }


    fun getUserInfoFromDatastore(): Flow<Pair<String, String>> =
        context.userDatastore.data.map { preferences ->
            val userId = preferences[PreferencesKeys.USER_ID_KEY]
            val nickname = preferences[PreferencesKeys.USER_NICKNAME_KEY]

            if (userId != null && nickname != null) {
                Pair(userId, nickname)
            } else {
                Pair("", "")
            }
        }

    suspend fun clearUserInfo() =
        context.userDatastore.edit { preferences ->
            preferences.remove(PreferencesKeys.USER_ID_KEY)
            preferences.remove(PreferencesKeys.USER_NICKNAME_KEY)
        }

}