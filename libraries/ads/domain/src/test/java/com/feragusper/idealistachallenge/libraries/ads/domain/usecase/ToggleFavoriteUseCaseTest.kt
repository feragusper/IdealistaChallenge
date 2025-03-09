package com.feragusper.idealistachallenge.libraries.ads.domain.usecase

import com.feragusper.idealistachallenge.libraries.ads.domain.repository.AdsRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ToggleFavoriteUseCaseTest {

    private val repository: AdsRepository = mockk(relaxed = true)
    private val useCase = ToggleFavoriteUseCase(repository)

    @Nested
    @DisplayName("GIVEN a request to toggle favorite")
    inner class ToggleFavoriteTests {

        @Test
        fun `WHEN invoked THEN repository toggles favorite successfully`() = runTest {
            // GIVEN
            val adId = "123"
            coEvery { repository.toggleFavorite(adId) } returns Unit

            // WHEN
            val result = useCase(adId)

            // THEN
            result.isSuccess shouldBe true
            coVerify(exactly = 1) { repository.toggleFavorite(adId) }
        }

        @Test
        fun `WHEN invoked multiple times THEN repository is called correctly`() = runTest {
            // GIVEN
            val adId = "456"
            coEvery { repository.toggleFavorite(adId) } returns Unit

            // WHEN
            useCase(adId)
            useCase(adId)

            // THEN
            coVerify(exactly = 2) { repository.toggleFavorite(adId) }
        }

        @Test
        fun `WHEN repository throws an exception THEN use case returns failure`() = runTest {
            // GIVEN
            val adId = "789"
            val exception = RuntimeException("Database error")
            coEvery { repository.toggleFavorite(adId) } throws exception

            // WHEN
            val result = useCase(adId)

            // THEN
            result.isFailure shouldBe true
            result.exceptionOrNull()?.message shouldBe "Database error"
            coVerify(exactly = 1) { repository.toggleFavorite(adId) }
        }
    }
}
