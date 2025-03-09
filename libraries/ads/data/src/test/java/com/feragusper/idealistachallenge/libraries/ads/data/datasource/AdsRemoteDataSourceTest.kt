package com.feragusper.idealistachallenge.libraries.ads.data.datasource

import com.feragusper.idealistachallenge.libraries.ads.data.api.AdsApiService
import com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity.AdDetailEntity
import com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity.AdSummaryEntity
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AdsRemoteDataSourceTest {

    private val apiService: AdsApiService = mockk()
    private lateinit var remoteDataSource: AdsRemoteDataSource

    private val mockSummaryList = listOf(
        mockk<AdSummaryEntity>(relaxed = true),
        mockk<AdSummaryEntity>(relaxed = true)
    )

    private val mockDetail = mockk<AdDetailEntity>(relaxed = true)

    @BeforeEach
    fun setUp() {
        remoteDataSource = AdsRemoteDataSource(apiService)
    }

    @Nested
    @DisplayName("GIVEN ads list is requested")
    inner class GetAdsListTests {

        @Test
        fun `WHEN API returns data THEN it is emitted as Flow`() = runTest {
            // GIVEN
            coEvery { apiService.getAdsList() } returns mockSummaryList

            // WHEN
            val result = remoteDataSource.getAdsList().first()

            // THEN
            result shouldBe mockSummaryList
            coVerify(exactly = 1) { apiService.getAdsList() }
        }
    }

    @Nested
    @DisplayName("GIVEN ad detail is requested")
    inner class GetAdDetailTests {

        @Test
        fun `WHEN API returns detail THEN it is emitted as Flow`() = runTest {
            // GIVEN
            coEvery { apiService.getAdDetail() } returns mockDetail

            // WHEN
            val result = remoteDataSource.getAdDetail().first()

            // THEN
            result shouldBe mockDetail
            coVerify(exactly = 1) { apiService.getAdDetail() }
        }
    }
}
