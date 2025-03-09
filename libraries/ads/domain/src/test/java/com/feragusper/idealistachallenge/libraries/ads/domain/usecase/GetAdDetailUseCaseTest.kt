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

class GetAdDetailUseCaseTest {

    private val repository: AdsRepository = mockk(relaxed = true)
    private val useCase = GetAdDetailUseCase(repository)

    private val mockAdDetail: Ad.Detail = mockk()

    @Nested
    @DisplayName("GIVEN a valid ad ID")
    inner class ValidAd {

        @Test
        fun `WHEN getting ad details THEN return the expected ad`() = runTest {
            coEvery { repository.getAdDetail() } returns flowOf(mockAdDetail)

            val result = useCase()

            result.test {
                awaitItem().getOrNull() shouldBe mockAdDetail
                awaitComplete()
            }

            coVerify { repository.getAdDetail() }
        }
    }

    @Nested
    @DisplayName("GIVEN an error in the repository")
    inner class RepositoryFailure {

        @Test
        fun `WHEN repository throws an error THEN emit failure`() = runTest {
            val exception = RuntimeException("Network error")
            coEvery { repository.getAdDetail() } returns flow { throw exception }

            val result = useCase()

            result.test {
                awaitItem().exceptionOrNull()?.message shouldBe "Network error"
                awaitComplete()
            }

            coVerify { repository.getAdDetail() }
        }
    }
}
