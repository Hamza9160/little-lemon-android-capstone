package com.littlelemon.app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Single DataStore instance for the whole process.
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "little_lemon_prefs")

/** Immutable snapshot of the data captured during onboarding. */
data class UserProfile(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = ""
) {
    val isRegistered: Boolean
        get() = firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()
}

/**
 * Persists the user's onboarding details so they survive an app restart,
 * and clears them on log out. Backed by Preferences DataStore.
 */
class UserPreferencesRepository(private val context: Context) {

    private object Keys {
        val FIRST_NAME = stringPreferencesKey("first_name")
        val LAST_NAME = stringPreferencesKey("last_name")
        val EMAIL = stringPreferencesKey("email")
    }

    val userProfile: Flow<UserProfile> = context.dataStore.data.map { prefs ->
        UserProfile(
            firstName = prefs[Keys.FIRST_NAME].orEmpty(),
            lastName = prefs[Keys.LAST_NAME].orEmpty(),
            email = prefs[Keys.EMAIL].orEmpty()
        )
    }

    suspend fun saveProfile(firstName: String, lastName: String, email: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.FIRST_NAME] = firstName.trim()
            prefs[Keys.LAST_NAME] = lastName.trim()
            prefs[Keys.EMAIL] = email.trim()
        }
    }

    /** Log out: wipe every stored value so the Profile screen is empty again. */
    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}
