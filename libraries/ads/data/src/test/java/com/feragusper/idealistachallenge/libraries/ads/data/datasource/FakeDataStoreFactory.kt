package com.feragusper.idealistachallenge.libraries.ads.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

/**
 * Provides a Fake DataStore implementation for testing.
 */
object FakeDataStoreFactory {
    fun create(): DataStore<Preferences> {
        val fakePreferences = MutableStateFlow(emptyPreferences())

        return object : DataStore<Preferences> {
            override val data = fakePreferences.map { it }

            override suspend fun updateData(transform: suspend (Preferences) -> Preferences): Preferences {
                val newPreferences = transform(fakePreferences.value)
                fakePreferences.value = newPreferences
                return newPreferences
            }
        }
    }
}