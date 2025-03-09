package com.feragusper.idealistachallenge.libraries.ads.data.repository

import com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity.AdDetailEntity
import com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity.AdDetailEntity.EnergyCertificationEntity.EnergyTypeEntity
import com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity.AdSummaryEntity
import com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity.AdSummaryEntity.ParkingEntity
import com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity.AdSummaryEntity.PriceInfoWrapperEntity
import com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity.ImageEntity
import com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity.MultimediaEntity
import com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity.PriceInfoEntity
import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AdMapperTest {

    private lateinit var mapper: AdMapper

    @BeforeEach
    fun setUp() {
        mapper = AdMapper()
    }

    @Nested
    @DisplayName("GIVEN an AdSummaryEntity")
    inner class MapToSummary {

        @Test
        fun `WHEN mapping to domain THEN properties should match`() {
            // GIVEN
            val entity = AdSummaryEntity(
                id = "1",
                priceInfo = PriceInfoWrapperEntity(PriceInfoEntity(120000.0, "€")),
                operation = "sale",
                propertyType = "flat",
                rooms = 3,
                bathrooms = 2,
                size = 150.0,
                floor = "2",
                description = "A nice flat in Madrid",
                exterior = true,
                address = "Madrid, Spain",
                province = "Madrid",
                district = "Centro",
                neighborhood = "Sol",
                latitude = 40.4168,
                longitude = -3.7038,
                multimedia = MultimediaEntity(
                    listOf(
                        ImageEntity(
                            url = "https://example.com/img1.jpg",
                            tag = "main",
                            localizedName = "Main Image",
                            multimediaId = 123
                        )
                    )
                ),
                parkingSpace = ParkingEntity(
                    hasParkingSpace = true,
                    isParkingSpaceIncludedInPrice = false
                ),
                features = AdSummaryEntity.FeaturesEntity(
                    hasSwimmingPool = true,
                    hasTerrace = false,
                    hasAirConditioning = true,
                    hasBoxRoom = false,
                    hasGarden = true
                )
            )

            // WHEN
            val result = mapper.mapSummary(entity)

            // THEN
            result.id shouldBe entity.id
            result.price.amount shouldBe entity.priceInfo.price.amount
            result.price.currencySuffix shouldBe entity.priceInfo.price.currencySuffix
            result.operation shouldBe Ad.OperationType.SALE
            result.propertyType shouldBe Ad.PropertyType.FLAT
            result.rooms shouldBe entity.rooms
            result.bathrooms shouldBe entity.bathrooms
            result.size shouldBe entity.size.toInt()
            result.exterior shouldBe entity.exterior
            result.floor shouldBe entity.floor
            result.address shouldBe entity.address
            result.province shouldBe entity.province
            result.district shouldBe entity.district
            result.neighborhood shouldBe entity.neighborhood
            result.images shouldBe listOf("https://example.com/img1.jpg")
            result.parkingSpace shouldBe Ad.Summary.ParkingSpace(true, false)
            result.features shouldBe Ad.Summary.Features(
                hasSwimmingPool = true,
                hasTerrace = false,
                hasAirConditioning = true,
                hasBoxRoom = false,
                hasGarden = true
            )
        }
    }

    @Nested
    @DisplayName("GIVEN an AdDetailEntity")
    inner class MapToDetail {

        @Test
        fun `WHEN mapping to domain THEN properties should match`() {
            // GIVEN
            val entity = AdDetailEntity(
                id = 1,
                priceInfo = PriceInfoEntity(120000.0, "€"),
                operation = "rent",
                homeType = "flat",
                extendedPropertyType = "penthouse",
                state = "available",
                propertyComment = "Spacious penthouse in Barcelona",
                characteristics = AdDetailEntity.CharacteristicsEntity(
                    roomNumber = 4,
                    bathNumber = 3,
                    constructedArea = 200,
                    flatLocation = "central",
                    communityCosts = 300.0,
                    housingFurnitures = "furnished",
                    agencyIsABank = false,
                    lift = true,
                    boxroom = false,
                    isDuplex = true,
                    status = "renew",
                    exterior = true,
                    modificationDate = mockk(),
                    energyCertificationType = "b",
                    floor = "1"
                ),
                multimedia = MultimediaEntity(
                    listOf(
                        ImageEntity(
                            url = "https://example.com/img1.jpg",
                            tag = "main",
                            localizedName = "Main Image",
                            multimediaId = 123
                        )
                    )
                ),
                location = AdDetailEntity.LocationEntity(41.3851, 2.1734),
                energyCertification = AdDetailEntity.EnergyCertificationEntity(
                    title = "A",
                    energyConsumption = EnergyTypeEntity(
                        type = "b"
                    ),
                    emissions = EnergyTypeEntity(
                        type = "b"
                    )
                ),
                propertyType = "flat"
            )

            // WHEN
            val result = mapper.mapDetail(entity)

            // THEN
            result.id shouldBe entity.id
            result.price.amount shouldBe entity.priceInfo.amount
            result.price.currencySuffix shouldBe entity.priceInfo.currencySuffix
            result.operation shouldBe Ad.OperationType.RENT
            result.propertyType shouldBe Ad.PropertyType.FLAT
            result.extendedPropertyType shouldBe entity.extendedPropertyType
            result.state shouldBe entity.state
            result.description shouldBe entity.propertyComment
            result.rooms shouldBe entity.characteristics.roomNumber
            result.bathrooms shouldBe entity.characteristics.bathNumber
            result.size shouldBe entity.characteristics.constructedArea
            result.flatLocation shouldBe entity.characteristics.flatLocation
            result.exterior shouldBe entity.characteristics.exterior
            result.floor shouldBe entity.characteristics.floor
            result.images shouldBe listOf("https://example.com/img2.jpg")
            result.location shouldBe Ad.Location(
                entity.location.latitude,
                entity.location.longitude
            )
            result.characteristics shouldBe Ad.Detail.Characteristics(
                communityCosts = entity.characteristics.communityCosts,
                housingFurniture = entity.characteristics.housingFurnitures,
                agencyIsABank = entity.characteristics.agencyIsABank,
                lift = entity.characteristics.lift,
                boxroom = entity.characteristics.boxroom,
                isDuplex = entity.characteristics.isDuplex,
                status = Ad.PropertyStatus.RENEWED
            )
            result.energyCertification shouldBe Ad.Detail.EnergyCertification(
                title = entity.energyCertification.title,
                energyConsumption = entity.energyCertification.energyConsumption.type,
                emissions = entity.energyCertification.emissions.type
            )
        }
    }
}
