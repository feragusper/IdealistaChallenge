package com.feragusper.idealistachallenge.libraries.ads.domain.model

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.Instant

class AdTest {

    private lateinit var samplePrice: Price
    private lateinit var sampleLocation: Ad.Location
    private lateinit var sampleImages: List<String>

    @BeforeEach
    fun setup() {
        samplePrice = Price(100000.0, "€")
        sampleLocation = Ad.Location(40.0, -3.0)
        sampleImages = listOf("https://example.com/image1.jpg")
    }

    @Nested
    @DisplayName("GIVEN an Ad Summary")
    inner class AdSummaryTest {

        @Test
        fun `WHEN ad is created without favorite date THEN isFavorite should be false`() {
            // GIVEN
            val adSummary = Ad.Summary(
                id = "1",
                price = samplePrice,
                operation = Ad.OperationType.SALE,
                propertyType = Ad.PropertyType.FLAT,
                size = 100,
                rooms = 3,
                bathrooms = 2,
                exterior = true,
                floor = "2",
                location = sampleLocation,
                description = "Nice flat in Madrid",
                images = sampleImages,
                favoriteDate = null, // No favorito
                address = "Calle Gran Via",
                province = "Madrid",
                district = "Centro",
                neighborhood = "Sol",
                parkingSpace = Ad.Summary.ParkingSpace(true, false),
                features = Ad.Summary.Features(
                    hasSwimmingPool = false,
                    hasTerrace = true,
                    hasAirConditioning = true,
                    hasBoxRoom = false,
                    hasGarden = false
                )
            )

            // THEN
            adSummary.isFavorite shouldBe false
        }

        @Test
        fun `WHEN ad is marked as favorite THEN isFavorite should be true`() {
            // GIVEN
            val favoriteDate = Instant.now()
            val adSummary = Ad.Summary(
                id = "2",
                price = samplePrice,
                operation = Ad.OperationType.RENT,
                propertyType = Ad.PropertyType.FLAT,
                size = 200,
                rooms = 5,
                bathrooms = 3,
                exterior = true,
                floor = "1",
                location = sampleLocation,
                description = "Big house with garden",
                images = sampleImages,
                favoriteDate = favoriteDate, // Marcado como favorito
                address = "Calle Serrano",
                province = "Madrid",
                district = "Salamanca",
                neighborhood = "Recoletos",
                parkingSpace = Ad.Summary.ParkingSpace(true, true),
                features = Ad.Summary.Features(
                    hasSwimmingPool = true,
                    hasTerrace = true,
                    hasAirConditioning = true,
                    hasBoxRoom = true,
                    hasGarden = true
                )
            )

            // THEN
            adSummary.isFavorite shouldBe true
        }
    }

    @Nested
    @DisplayName("GIVEN an Ad Detail")
    inner class AdDetailTest {

        @Test
        fun `WHEN ad is created without favorite date THEN isFavorite should be false`() {
            // GIVEN
            val adDetail = Ad.Detail(
                id = "3",
                price = samplePrice,
                operation = Ad.OperationType.SALE,
                propertyType = Ad.PropertyType.FLAT,
                size = 150,
                rooms = 4,
                bathrooms = 3,
                exterior = true,
                floor = "5",
                location = sampleLocation,
                description = "Luxury penthouse with city views",
                images = sampleImages,
                favoriteDate = null, // No favorito
                extendedPropertyType = "penthouse",
                homeType = "duplex",
                state = "available",
                flatLocation = "corner",
                characteristics = Ad.Detail.Characteristics(
                    communityCosts = 150.0,
                    housingFurniture = "furnished",
                    agencyIsABank = false,
                    lift = true,
                    boxroom = true,
                    isDuplex = true,
                    status = Ad.PropertyStatus.RENEWED
                ),
                modificationDate = Instant.now(),
                energyCertification = Ad.Detail.EnergyCertification(
                    title = "A",
                    energyConsumption = "low",
                    emissions = "low"
                )
            )

            // THEN
            adDetail.isFavorite shouldBe false
        }

        @Test
        fun `WHEN ad is marked as favorite THEN isFavorite should be true`() {
            // GIVEN
            val favoriteDate = Instant.now()
            val adDetail = Ad.Detail(
                id = "4",
                price = samplePrice,
                operation = Ad.OperationType.RENT,
                propertyType = Ad.PropertyType.FLAT,
                size = 300,
                rooms = 6,
                bathrooms = 4,
                exterior = true,
                floor = "ground",
                location = sampleLocation,
                description = "Private villa with pool",
                images = sampleImages,
                favoriteDate = favoriteDate, // Marcado como favorito
                extendedPropertyType = "villa",
                homeType = "independent",
                state = "available",
                flatLocation = "suburban",
                characteristics = Ad.Detail.Characteristics(
                    communityCosts = 500.0,
                    housingFurniture = "unfurnished",
                    agencyIsABank = false,
                    lift = false,
                    boxroom = true,
                    isDuplex = false,
                    status = Ad.PropertyStatus.RENEWED
                ),
                modificationDate = Instant.now(),
                energyCertification = Ad.Detail.EnergyCertification(
                    title = "B",
                    energyConsumption = "medium",
                    emissions = "medium"
                )
            )

            // THEN
            adDetail.isFavorite shouldBe true
        }
    }
}
