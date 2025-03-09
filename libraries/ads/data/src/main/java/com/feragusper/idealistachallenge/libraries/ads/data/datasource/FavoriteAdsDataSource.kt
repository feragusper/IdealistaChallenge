package com.feragusper.idealistachallenge.libraries.ads.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Represents a data source for managing favorite ads.
 *
 * @param dataStore The [DataStore] instance for storing favorite ads.
 */
@Singleton
internal class FavoriteAdsDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    /**
     * Retrieves a map of favorite ads.
     *
     * @return A [Flow] emitting a map of favorite ads.
     */
    fun getFavorites(): Flow<Map<String, Long>> = dataStore.data
        .map { preferences ->
            preferences.asMap()
                .mapKeys { it.key.name }
                .mapValues { it.value as Long }
        }

    /**
     * Retrieves the timestamp of a favorite ad.
     *
     * @param adId The ID of the ad.
     * @return A [Flow] emitting the timestamp of the favorite ad.
     */
    fun getFavorite(adId: String): Flow<Long?> =
        dataStore.data.map { preferences ->
            val key = longPreferencesKey(adId)
            preferences[key]
        }

    /**
     * Adds a favorite ad.
     *
     * @param adId The ID of the ad.
     * @param timestamp The timestamp of the favorite ad.
     */
    suspend fun addFavorite(adId: String, timestamp: Long) {
        val key = longPreferencesKey(adId)

        dataStore.edit { preferences ->
            preferences[key] = timestamp
        }
    }

    /**
     * Removes a favorite ad.
     *
     * @param adId The ID of the ad.
     */
    suspend fun removeFavorite(adId: String) {
        val key = longPreferencesKey(adId)
        dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    /**
     * Checks if an ad is a favorite.
     *
     * @param adId The ID of the ad.
     * @return A [Flow] emitting a boolean indicating if the ad is a favorite.
     */
    fun isFavorite(adId: String): Flow<Boolean> =
        dataStore.data.map { preferences ->
            val key = longPreferencesKey(adId)
            preferences.contains(key)
        }

}
