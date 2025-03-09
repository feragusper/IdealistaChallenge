package com.feragusper.idealistachallenge.libraries.ads.domain.usecase

import app.cash.turbine.test
import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import com.feragusper.idealistachallenge.libraries.ads.domain.repository.AdsRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class GetFavoriteAdsListUseCaseTest {

    private val repository: AdsRepository = mockk()

    private val useCase = GetFavoriteAdsListUseCase(repository)

    private val mockAdSummary = mockk<Ad.Summary>(relaxed = true)

    @Nested
    @DisplayName("GIVEN a request for favorite ads list")
    inner class FavoriteAdsListTests {

        @Test
        fun `WHEN repository returns favorites THEN use case emits them`() = runTest {
            // GIVEN
            coEvery { repository.getFavoriteAdsList() } returns flowOf(listOf(mockAdSummary))

            // WHEN
            val result = useCase()

            // THEN
            result.test {
                awaitItem() shouldBe Result.success(listOf(mockAdSummary))
                awaitComplete()
            }
        }

        @Test
        fun `WHEN repository returns empty list THEN use case emits empty list`() = runTest {
            // GIVEN
            coEvery { repository.getFavoriteAdsList() } returns flowOf(emptyList())

            // WHEN
            val result = useCase()

            // THEN
            result.test {
                awaitItem() shouldBe Result.success(emptyList())
                awaitComplete()
            }
        }

        @Test
        fun `WHEN repository throws an exception THEN use case emits failure`() = runTest {
            // GIVEN
            val exception = RuntimeException("Database error")
            coEvery { repository.getFavoriteAdsList() } returns flow { throw exception }

            // WHEN
            val result = useCase()

            // THEN
            result.test {
                val emittedResult = awaitItem()
                emittedResult.isFailure shouldBe true
                emittedResult.exceptionOrNull()?.message shouldBe "Database error"
                awaitComplete()
            }
        }
    }
}
