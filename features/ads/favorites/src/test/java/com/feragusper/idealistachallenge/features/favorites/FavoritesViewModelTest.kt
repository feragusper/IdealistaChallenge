package com.feragusper.idealistachallenge.features.favorites

import app.cash.turbine.test
import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import com.feragusper.idealistachallenge.libraries.ads.domain.usecase.GetFavoriteAdsListUseCase
import com.feragusper.idealistachallenge.libraries.ads.domain.usecase.ToggleFavoriteUseCase
import com.feragusper.idealistachallenge.libraries.ads.presentation.AdSummaryUi
import com.feragusper.idealistachallenge.libraries.ads.presentation.mapper.AdSummaryUiMapper
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class FavoritesViewModelTest {

    private val getFavoritesUseCase: GetFavoriteAdsListUseCase = mockk(relaxed = true)
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mockk(relaxed = true)
    private val adSummaryUiMapper: AdSummaryUiMapper = mockk(relaxed = true)

    @Nested
    @DisplayName("GIVEN favorites list is requested")
    inner class FetchFavoritesListTests {

        @Test
        fun `WHEN use case returns favorites THEN UI state updates correctly`() = runTest {
            // GIVEN
            val domainAds = listOf(mockk<Ad.Summary>())
            val uiAds = listOf(mockk<AdSummaryUi>())

            coEvery { getFavoritesUseCase() } returns flowOf(Result.success(domainAds))
            every { adSummaryUiMapper.map(any()) } returns uiAds.first()

            val viewModel =
                FavoritesViewModel(
                    getFavoritesUseCase = getFavoritesUseCase,
                    toggleFavoriteUseCase = toggleFavoriteUseCase,
                    mapper = adSummaryUiMapper
                )

            viewModel.uiState.test {
                awaitItem() shouldBe emptyList() // Initial state
                awaitItem() shouldBe uiAds // Updated state
            }
        }
    }

    @Nested
    @DisplayName("GIVEN ad favorite status is toggled")
    inner class ToggleFavoriteTests {

        @Test
        fun `WHEN favorite toggle fails THEN error state updates`() = runTest {
            // GIVEN
            val adId = "1"
            val errorMessage = "Favorite action failed"
            coEvery { toggleFavoriteUseCase(adId) } returns Result.failure(Exception(errorMessage))

            val viewModel =
                FavoritesViewModel(
                    getFavoritesUseCase = getFavoritesUseCase,
                    toggleFavoriteUseCase = toggleFavoriteUseCase,
                    mapper = adSummaryUiMapper
                )

            // WHEN
            viewModel.toggleFavorite(adId)

            // THEN
            viewModel.error.test {
                awaitItem() shouldBe null // Initial state
                awaitItem() shouldBe errorMessage // Error updated
            }
        }
    }
}
