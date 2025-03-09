package com.feragusper.idealistachallenge.features.detail.presentation.ui

import com.feragusper.idealistachallenge.features.detail.presentation.R
import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import com.feragusper.idealistachallenge.libraries.ads.domain.model.Price
import com.feragusper.idealistachallenge.libraries.design.StringResource
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class AdDetailUiMapperTest {

    private lateinit var mapper: AdDetailUiMapper

    @BeforeEach
    fun setup() {
        mapper = AdDetailUiMapper()
    }

    @Nested
    @DisplayName("GIVEN a detailed ad")
    inner class MapAdDetailTests {

        @Test
        fun `WHEN mapped THEN all fields are correctly transformed`() {
            // GIVEN
            val fixedInstant = Instant.parse("2024-03-01T12:00:00Z")
            val formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.systemDefault())
            val expectedFormattedDate = formatter.format(fixedInstant)

            val ad = Ad.Detail(
                id = "123",
                images = listOf("image1.jpg", "image2.jpg"),
                propertyType = Ad.PropertyType.FLAT,
                operation = Ad.OperationType.SALE,
                floor = "3",
                exterior = true,
                price = Price(amount = 250000.0, currencySuffix = "EUR"),
                rooms = 3,
                size = 100,
                bathrooms = 2,
                description = "A beautiful flat in the city center",
                characteristics = Ad.Detail.Characteristics(
                    status = Ad.PropertyStatus.RENEWED,
                    lift = true,
                    boxroom = false,
                    communityCosts = 100.0,
                    housingFurniture = "furnished",
                    agencyIsABank = false,
                    isDuplex = false
                ),
                state = "state",
                modificationDate = fixedInstant,
                energyCertification = Ad.Detail.EnergyCertification(
                    title = "title",
                    energyConsumption = "A",
                    emissions = "B"
                ),
                favoriteDate = fixedInstant,
                flatLocation = "flatLocation",
                extendedPropertyType = "extendedPropertyType",
                homeType = "homeType",
                location = Ad.Location(
                    latitude = 40.7128,
                    longitude = -74.0060
                )
            )

            // WHEN
            val result = mapper.map(ad)

            // THEN
            result.id shouldBe ad.id
            result.images shouldBe ad.images
            result.isFavorite shouldBe true

            result.price shouldBe "250.000 EUR"

            (result.size as StringResource.Resource).resId shouldBe R.string.ad_size
            (result.size as StringResource.Resource).args shouldBe listOf(StringResource.Raw("100"))

            (result.rooms as StringResource.Resource).resId shouldBe R.string.ad_rooms
            (result.rooms as StringResource.Resource).args shouldBe listOf(StringResource.Raw("3"))

            (result.bathrooms as StringResource.Resource).resId shouldBe R.string.ad_bathrooms
            (result.bathrooms as StringResource.Resource).args shouldBe listOf(StringResource.Raw("2"))

            (result.energyCertification as StringResource.Resource).resId shouldBe R.string.ad_energy_certification
            (result.energyCertification as StringResource.Resource).args shouldBe listOf(
                StringResource.Raw("A"),
                StringResource.Raw("B")
            )

            (result.favoriteDate as StringResource.Resource).resId shouldBe R.string.ad_favorite_date
            (result.favoriteDate as StringResource.Resource).args.first() shouldBe StringResource.Raw(
                expectedFormattedDate
            )

            (result.title as StringResource.Resource).resId shouldBe R.string.ad_detail_title

            (result.subtitle as StringResource.Resource).resId shouldBe R.string.ad_detail_subtitle

            (result.characteristics as StringResource.Resource).resId shouldBe R.string.ad_characteristics

            (result.building as StringResource.Resource).resId shouldBe R.string.ad_building

            (result.pricePerSquareMeter as StringResource.Resource).resId shouldBe R.string.ad_price_per_square_meter

            (result.modificationDate as StringResource.Resource).resId shouldBe R.string.ad_modification_date
        }
    }
}
