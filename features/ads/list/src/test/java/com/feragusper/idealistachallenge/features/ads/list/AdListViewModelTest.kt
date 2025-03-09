package com.feragusper.idealistachallenge.features.ads.list

import app.cash.turbine.test
import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import com.feragusper.idealistachallenge.libraries.ads.domain.usecase.GetAdsListUseCase
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

class AdListViewModelTest {

    private val getAdsListUseCase: GetAdsListUseCase = mockk(relaxed = true)
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mockk(relaxed = true)
    private val adSummaryUiMapper: AdSummaryUiMapper = mockk(relaxed = true)

    @Nested
    @DisplayName("GIVEN ads list is requested")
    inner class FetchAdsListTests {

        @Test
        fun `WHEN use case returns ads THEN UI state updates correctly`() = runTest {
            // GIVEN
            val domainAds = listOf(mockk<Ad.Summary>())
            val uiAds = listOf(mockk<AdSummaryUi>())

            coEvery { getAdsListUseCase() } returns flowOf(Result.success(domainAds))
            every { adSummaryUiMapper.map(any()) } returns uiAds.first()

            val viewModel =
                AdListViewModel(
                    getAdsListUseCase = getAdsListUseCase,
                    toggleFavoriteUseCase = toggleFavoriteUseCase,
                    adSummaryUiMapper = adSummaryUiMapper
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
                AdListViewModel(
                    getAdsListUseCase = getAdsListUseCase,
                    toggleFavoriteUseCase = toggleFavoriteUseCase,
                    adSummaryUiMapper = adSummaryUiMapper
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
