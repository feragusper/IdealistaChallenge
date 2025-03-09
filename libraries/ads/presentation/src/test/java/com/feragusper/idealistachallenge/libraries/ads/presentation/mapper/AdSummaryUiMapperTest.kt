package com.feragusper.idealistachallenge.libraries.ads.presentation.mapper

import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import com.feragusper.idealistachallenge.libraries.ads.domain.model.Price
import com.feragusper.idealistachallenge.libraries.ads.presentation.R
import com.feragusper.idealistachallenge.libraries.design.StringResource
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.Instant

class AdSummaryUiMapperTest {

    private lateinit var mapper: AdSummaryUiMapper

    @BeforeEach
    fun setup() {
        mapper = AdSummaryUiMapper()
    }

    @Nested
    @DisplayName("GIVEN an ad summary")
    inner class MapAdSummaryTests {

        @Test
        fun `WHEN mapped THEN all fields are correctly transformed`() {
            // GIVEN
            val favoriteDate = Instant.parse("2024-03-01T12:00:00Z") // Fixed date for testing
            val ad = Ad.Summary(
                id = "123",
                price = Price(amount = 250000.0, currencySuffix = "EUR"),
                propertyType = Ad.PropertyType.FLAT,
                operation = Ad.OperationType.SALE,
                size = 100,
                rooms = 3,
                bathrooms = 2,
                exterior = true,
                floor = "3",
                location = Ad.Location(latitude = 40.7128, longitude = -74.0060),
                description = "A beautiful flat in the city center",
                images = listOf("image1.jpg", "image2.jpg"),
                favoriteDate = favoriteDate,
                address = "123 Main Street",
                province = "Madrid",
                district = "Central",
                neighborhood = "Downtown",
                parkingSpace = Ad.Summary.ParkingSpace(
                    hasParkingSpace = true,
                    isIncludedInPrice = true
                ),
                features = Ad.Summary.Features(
                    hasSwimmingPool = true,
                    hasTerrace = true,
                    hasAirConditioning = true,
                    hasBoxRoom = true,
                    hasGarden = false
                )
            )

            // WHEN
            val result = mapper.map(ad)

            // THEN
            result.id shouldBe ad.id
            result.images shouldBe ad.images
            result.isFavorite shouldBe ad.isFavorite
            result.favoriteDate shouldBe ad.favoriteDate

            // Title assertions
            (result.title as StringResource.Resource).resId shouldBe R.string.ad_title

            // Subtitle assertions
            (result.subtitle as StringResource.Resource).resId shouldBe R.string.ad_subtitle

            // Price assertion
            result.price shouldBe "250.000 EUR"

            // Size assertion
            (result.size as StringResource.Resource).resId shouldBe R.string.ad_size

            // Rooms assertion
            (result.rooms as StringResource.Resource).resId shouldBe R.string.ad_rooms

            // Operation Type assertion
            (result.operationType as StringResource.Resource).resId shouldBe R.string.ad_sale

            // Parking assertion
            (result.parking as StringResource.Resource).resId shouldBe R.string.ad_parking

            // Extra features assertion
            result.extra.map { (it as StringResource.Resource).resId } shouldBe listOf(
                R.string.ad_parking,
                R.string.ad_has_boxroom,
                R.string.ad_has_swimmingPool,
                R.string.ad_has_terrace,
                R.string.ad_has_air_conditioning
            )
        }
    }
}
