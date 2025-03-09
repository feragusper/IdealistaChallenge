package com.feragusper.idealistachallenge.libraries.ads.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class FavoriteAdsDataSourceTest {

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var favoriteAdsDataSource: FavoriteAdsDataSource

    private val adId = "123"
    private val timestamp = 1672531200000L

    @BeforeEach
    fun setUp() {
        dataStore = FakeDataStoreFactory.create()
        favoriteAdsDataSource = FavoriteAdsDataSource(dataStore)
    }

    @Nested
    @DisplayName("GIVEN a favorite ad")
    inner class FavoriteAdTests {

        @Test
        fun `WHEN added THEN it should be retrievable`() = runTest {
            // WHEN
            favoriteAdsDataSource.addFavorite(adId, timestamp)

            // THEN
            val result = favoriteAdsDataSource.getFavorite(adId).first()
            result shouldBe timestamp
        }

        @Test
        fun `WHEN removed THEN it should no longer exist`() = runTest {
            // GIVEN
            favoriteAdsDataSource.addFavorite(adId, timestamp)

            // WHEN
            favoriteAdsDataSource.removeFavorite(adId)

            // THEN
            val result = favoriteAdsDataSource.getFavorite(adId).first()
            result.shouldBeNull()
        }
    }

    @Nested
    @DisplayName("GIVEN multiple favorite ads")
    inner class MultipleFavoritesTests {

        @Test
        fun `WHEN added THEN all should be retrievable`() = runTest {
            // GIVEN
            val anotherAdId = "456"
            val anotherTimestamp = 1672617600000L // 2 de enero de 2023

            favoriteAdsDataSource.addFavorite(adId, timestamp)
            favoriteAdsDataSource.addFavorite(anotherAdId, anotherTimestamp)

            // WHEN
            val result = favoriteAdsDataSource.getFavorites().first()

            // THEN
            result.size shouldBe 2
            result[adId] shouldBe timestamp
            result[anotherAdId] shouldBe anotherTimestamp
        }
    }

    @Test
    fun `WHEN checking favorite status THEN it returns correct value`() = runTest {
        // GIVEN
        favoriteAdsDataSource.addFavorite(adId, timestamp)

        // WHEN
        val isFavorite = favoriteAdsDataSource.isFavorite(adId).first()
        val isNotFavorite = favoriteAdsDataSource.isFavorite("999").first()

        // THEN
        isFavorite shouldBe true
        isNotFavorite shouldBe false
    }
}