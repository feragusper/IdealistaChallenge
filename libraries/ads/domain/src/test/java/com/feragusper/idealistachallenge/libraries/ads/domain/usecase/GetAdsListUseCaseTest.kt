package com.feragusper.idealistachallenge.libraries.ads.domain.usecase

import app.cash.turbine.test
import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import com.feragusper.idealistachallenge.libraries.ads.domain.repository.AdsRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class GetAdsListUseCaseTest {

    private val repository: AdsRepository = mockk(relaxed = true)
    private val useCase = GetAdsListUseCase(repository)

    private val mockAds: List<Ad.Summary> = listOf(mockk(), mockk(), mockk())

    @Nested
    @DisplayName("GIVEN a request for ads list")
    inner class AdsListTests {

        @Test
        fun `WHEN repository returns ads THEN use case emits them`() = runTest {
            coEvery { repository.getAdsList() } returns flowOf(mockAds)

            val result = useCase()

            result.test {
                awaitItem().getOrNull() shouldBe mockAds
                awaitComplete()
            }

            coVerify { repository.getAdsList() }
        }

        @Test
        fun `WHEN repository returns empty list THEN use case emits empty list`() = runTest {
            coEvery { repository.getAdsList() } returns flowOf(emptyList())

            val result = useCase()

            result.test {
                awaitItem().getOrNull() shouldBe emptyList()
                awaitComplete()
            }

            coVerify { repository.getAdsList() }
        }

        @Test
        fun `WHEN repository throws an error THEN emit failure`() = runTest {
            val exception = RuntimeException("API failure")
            coEvery { repository.getAdsList() } returns flow { throw exception }

            val result = useCase()

            result.test {
                awaitItem().exceptionOrNull()?.message shouldBe "API failure"
                awaitComplete()
            }

            coVerify { repository.getAdsList() }
        }
    }
}
