package com.feragusper.idealistachallenge.features.detail.presentation.ui

import com.feragusper.idealistachallenge.features.detail.presentation.R
import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import com.feragusper.idealistachallenge.libraries.ads.presentation.mapper.StringResourceMapper
import com.feragusper.idealistachallenge.libraries.ads.presentation.utils.DateUtils.formatDate
import com.feragusper.idealistachallenge.libraries.ads.presentation.utils.PriceUtils.formatPrice
import com.feragusper.idealistachallenge.libraries.design.StringResource
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Locale
import javax.inject.Inject

/**
 * Mapper class for converting [Ad.Detail] to [AdDetailUi].
 */
class AdDetailUiMapper @Inject constructor() {

    /**
     * Maps an [Ad.Detail] to an [AdDetailUi].
     *
     * @param ad The [Ad.Detail] to be mapped.
     * @return The mapped [AdDetailUi].
     */
    fun map(ad: Ad.Detail): AdDetailUi {
        return AdDetailUi(
            id = ad.id,
            images = ad.images,
            title = StringResource.Resource(
                R.string.ad_detail_title,
                listOf(
                    StringResourceMapper.mapPropertyType(ad.propertyType),
                    StringResourceMapper.mapOperationType(ad.operation),
                    StringResource.Raw(ad.floor),
                    if (ad.exterior) StringResource.Resource(R.string.ad_exterior)
                    else StringResource.Resource(R.string.ad_interior)
                )
            ),
            price = formatPrice(ad.price),
            subtitle = StringResource.Resource(
                R.string.ad_detail_subtitle,
                listOfNotNull(
                    StringResourceMapper.mapPropertyStatus(ad.characteristics.status),
                    if (ad.characteristics.lift) {
                        StringResource.Resource(R.string.ad_has_lift)
                    } else {
                        null
                    }
                )
            ),
            rooms = StringResource.Resource(
                R.string.ad_rooms, listOf(StringResource.Raw(ad.rooms.toString()))
            ),
            size = StringResource.Resource(
                R.string.ad_size, listOf(StringResource.Raw(ad.size.toString()))
            ),
            bathrooms = StringResource.Resource(
                R.string.ad_bathrooms, listOf(StringResource.Raw(ad.bathrooms.toString()))
            ),
            description = ad.description,
            characteristics = mapCharacteristics(ad),
            building = mapBuildingInfo(ad),
            homeState = ad.state,
            totalPrice = formatPrice(ad.price),
            pricePerSquareMeter = StringResource.Resource(
                R.string.ad_price_per_square_meter,
                listOf(
                    StringResource.Raw(
                        NumberFormat.getNumberInstance(Locale("es", "ES"))
                            .format((ad.price.amount / ad.size).toInt())
                    )
                )
            ),
            modificationDate = StringResource.Resource(
                R.string.ad_modification_date,
                listOf(
                    StringResource.Raw(getDaysSinceModification(ad.modificationDate).toString())
                )
            ),
            energyCertification = mapEnergyCertification(ad),
            isFavorite = ad.favoriteDate != null,
            favoriteDate = ad.favoriteDate?.let {
                StringResource.Resource(
                    R.string.ad_favorite_date,
                    listOf(StringResource.Raw(formatDate(it)))
                )
            }
        )
    }

    /**
     * Maps property characteristics.
     *
     * @param ad The [Ad.Detail] containing the characteristics.
     * @return The mapped [StringResource] representing the characteristics.
     */
    private fun mapCharacteristics(ad: Ad.Detail): StringResource {
        return StringResource.Resource(
            R.string.ad_characteristics,
            listOf(
                StringResource.Raw(ad.size.toString()),
                StringResource.Raw(ad.rooms.toString()),
                StringResource.Raw(ad.bathrooms.toString()),
                if (ad.exterior) StringResource.Resource(R.string.ad_exterior)
                else StringResource.Resource(R.string.ad_interior),
                StringResource.Raw(ad.floor),
                StringResourceMapper.mapPropertyStatus(ad.characteristics.status)
            )
        )
    }

    /**
     * Maps building-related information.
     *
     * @param ad The [Ad.Detail] containing the building information.
     * @return The mapped [StringResource] representing the building information.
     */
    private fun mapBuildingInfo(ad: Ad.Detail): StringResource {
        return StringResource.Resource(
            R.string.ad_building,
            listOf(
                StringResource.Resource(
                    if (ad.characteristics.lift) R.string.ad_has_lift else R.string.ad_no_lift
                ),
                StringResource.Resource(
                    if (ad.characteristics.boxroom) R.string.ad_has_boxroom else R.string.ad_no_boxroom
                ),
                StringResource.Resource(
                    R.string.ad_community_costs,
                    listOf(
                        StringResource.Raw(
                            NumberFormat.getCurrencyInstance(Locale("es", "ES"))
                                .format(ad.characteristics.communityCosts)
                        )
                    )
                )
            )
        )
    }

    /**
     * Maps energy certification details.
     *
     * @param ad The [Ad.Detail] containing the energy certification details.
     * @return The mapped [StringResource] representing the energy certification details.
     */
    private fun mapEnergyCertification(ad: Ad.Detail): StringResource {
        return StringResource.Resource(
            R.string.ad_energy_certification,
            listOf(
                StringResource.Raw(ad.energyCertification.energyConsumption),
                StringResource.Raw(ad.energyCertification.emissions)
            )
        )
    }

    /**
     * Calculates the number of days since the ad was last modified.
     *
     * @param modificationDate The date and time when the ad was last modified.
     * @return The number of days since the ad was last modified.
     */
    private fun getDaysSinceModification(modificationDate: Instant): Long {
        val modificationLocalDate = modificationDate.atZone(ZoneId.systemDefault()).toLocalDate()
        val today = LocalDate.now(ZoneId.systemDefault())
        return ChronoUnit.DAYS.between(modificationLocalDate, today)
    }
}