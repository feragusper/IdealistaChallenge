package com.feragusper.idealistachallenge.features.detail.presentation

import app.cash.turbine.test
import com.feragusper.idealistachallenge.features.detail.presentation.ui.AdDetailUi
import com.feragusper.idealistachallenge.features.detail.presentation.ui.AdDetailUiMapper
import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import com.feragusper.idealistachallenge.libraries.ads.domain.usecase.GetAdDetailUseCase
import com.feragusper.idealistachallenge.libraries.ads.domain.usecase.ToggleFavoriteUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AdDetailViewModelTest {

    private val getAdDetailUseCase: GetAdDetailUseCase = mockk(relaxed = true)
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mockk(relaxed = true)
    private val adDetailUiMapper: AdDetailUiMapper = mockk(relaxed = true)

    @Nested
    @DisplayName("GIVEN ad detail is requested")
    inner class FetchAdDetailTests {

        @Test
        fun `WHEN use case returns ad detail THEN UI state updates correctly`() = runTest {
            // GIVEN
            val domainAd = mockk<Ad.Detail>()
            val uiAd = mockk<AdDetailUi>()

            coEvery { getAdDetailUseCase() } returns flowOf(Result.success(domainAd))
            coEvery { adDetailUiMapper.map(domainAd) } returns uiAd

            val viewModel = AdDetailViewModel(
                getAdDetailUseCase = getAdDetailUseCase,
                toggleFavoriteUseCase = toggleFavoriteUseCase,
                mapper = adDetailUiMapper
            )

            // THEN
            viewModel.uiState.test {
                awaitItem() shouldBe null // Initial state
                awaitItem() shouldBe uiAd // Updated state
            }
        }

    }

}
