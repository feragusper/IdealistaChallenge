package com.feragusper.idealistachallenge.libraries.ads.data.repository

import com.feragusper.idealistachallenge.libraries.ads.data.datasource.AdsRemoteDataSource
import com.feragusper.idealistachallenge.libraries.ads.data.datasource.FavoriteAdsDataSource
import com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity.AdDetailEntity
import com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity.AdSummaryEntity
import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.*
import java.time.Instant

class AdsRepositoryImplTest {

    private val remoteDataSource: AdsRemoteDataSource = mockk()
    private val favoriteAdsDataSource: FavoriteAdsDataSource = mockk()
    private val adMapper: AdMapper = mockk()

    private lateinit var repository: AdsRepositoryImpl

    @BeforeEach
    fun setUp() {
        repository = AdsRepositoryImpl(remoteDataSource, favoriteAdsDataSource, adMapper)
    }

    @Nested
    @DisplayName("GIVEN ads list is requested")
    inner class GetAdsListTests {

        @Test
        fun `WHEN remote data is available THEN it returns mapped Ad_Summary`() = runTest {
            // GIVEN
            val fakeEntityList: List<AdSummaryEntity> = listOf(mockk(), mockk()) // Mock entities
            val fakeAds: List<Ad.Summary> = listOf(mockk(), mockk()) // Mock domain models
            every { remoteDataSource.getAdsList() } returns flowOf(fakeEntityList)
            every { adMapper.mapSummary(any()) } returnsMany fakeAds
            every { favoriteAdsDataSource.getFavorites() } returns flowOf(emptyMap())

            // WHEN
            val result = repository.getAdsList().first()

            // THEN
            result.size shouldBe fakeAds.size
            verify(exactly = 1) { remoteDataSource.getAdsList() }
            verify(exactly = fakeAds.size) { adMapper.mapSummary(any()) }
        }

        @Test
        fun `WHEN ad is favorited THEN favorite date is included`() = runTest {
            // GIVEN
            val fakeAd: Ad.Summary = mockk(relaxed = true)
            val favoriteMap = mapOf("1" to Instant.now().toEpochMilli())

            every { remoteDataSource.getAdsList() } returns flowOf(listOf(mockk()))
            every { adMapper.mapSummary(any()) } returns fakeAd
            every { favoriteAdsDataSource.getFavorites() } returns flowOf(favoriteMap)

            // WHEN
            val result = repository.getAdsList().first()

            // THEN
            result.first().favoriteDate.shouldNotBeNull()
        }
    }

    @Nested
    @DisplayName("GIVEN ad detail is requested")
    inner class GetAdDetailTests {

        @Test
        fun `WHEN ad detail is available THEN it returns mapped Ad_Detail`() = runTest {
            // GIVEN
            val fakeEntity: AdDetailEntity = mockk() // Mock entity
            val fakeAdDetail: Ad.Detail = mockk() // Mock domain model

            every { remoteDataSource.getAdDetail() } returns flowOf(fakeEntity)
            every { adMapper.mapDetail(fakeEntity) } returns fakeAdDetail
            every { favoriteAdsDataSource.getFavorite(any()) } returns flowOf(null)

            // WHEN
            val result = repository.getAdDetail().first()

            // THEN
            result shouldBe fakeAdDetail
            verify(exactly = 1) { remoteDataSource.getAdDetail() }
            verify(exactly = 1) { adMapper.mapDetail(fakeEntity) }
        }
    }

    @Nested
    @DisplayName("GIVEN favorite status is toggled")
    inner class ToggleFavoriteTests {

        @Test
        fun `WHEN ad is favorited THEN it is stored with timestamp`() = runTest {
            // GIVEN
            every { favoriteAdsDataSource.isFavorite(any()) } returns flowOf(false)
            coEvery { favoriteAdsDataSource.addFavorite(any(), any()) } just Runs

            // WHEN
            repository.toggleFavorite("1")

            // THEN
            coVerify { favoriteAdsDataSource.addFavorite("1", any()) }
        }

        @Test
        fun `WHEN ad is unfavorited THEN it is removed from storage`() = runTest {
            // GIVEN
            every { favoriteAdsDataSource.isFavorite(any()) } returns flowOf(true)
            coEvery { favoriteAdsDataSource.removeFavorite(any()) } just Runs

            // WHEN
            repository.toggleFavorite("1")

            // THEN
            coVerify { favoriteAdsDataSource.removeFavorite("1") }
        }
    }
}
