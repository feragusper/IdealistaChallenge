package com.feragusper.idealistachallenge.libraries.ads.presentation.mapper

import com.feragusper.idealistachallenge.libraries.design.StringResource
import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import com.feragusper.idealistachallenge.libraries.ads.presentation.AdSummaryUi
import com.feragusper.idealistachallenge.libraries.ads.presentation.R
import com.feragusper.idealistachallenge.libraries.ads.presentation.utils.PriceUtils.formatPrice
import javax.inject.Inject

/**
 * Mapper class for converting [Ad.Summary] to [AdSummaryUi].
 */
class AdSummaryUiMapper @Inject constructor() {

    /**
     * Maps an [Ad.Summary] to an [AdSummaryUi].
     *
     * @param ad The [Ad.Summary] to be mapped.
     * @return The mapped [AdSummaryUi].
     */
    fun map(ad: Ad.Summary): AdSummaryUi {
        return AdSummaryUi(
            id = ad.id,
            images = ad.images,
            title = StringResource.Resource(
                R.string.ad_title,
                listOf(
                    StringResourceMapper.mapPropertyType(ad.propertyType),
                    StringResource.Raw(ad.address)
                )
            ),
            subtitle = StringResource.Resource(
                R.string.ad_subtitle,
                listOf(
                    StringResource.Raw(ad.neighborhood),
                    StringResource.Raw(ad.district),
                    StringResource.Raw(ad.province),
                )
            ),
            price = formatPrice(ad.price),
            parking = getParkingInfo(ad),
            size = StringResource.Resource(
                R.string.ad_size,
                listOf(StringResource.Raw(ad.size.toString()))
            ),
            rooms = StringResource.Resource(
                R.string.ad_rooms,
                listOf(StringResource.Raw(ad.rooms.toString()))
            ),
            isFavorite = ad.isFavorite,
            favoriteDate = ad.favoriteDate,
            operationType = StringResourceMapper.mapOperationType(ad.operation),
            extra = getExtraFeatures(ad)
        )
    }

    /**
     * Retrieves parking information if available.
     *
     * @param ad The [Ad.Summary] containing the parking information.
     * @return The [StringResource] representing the parking information, or null if not available.
     */
    private fun getParkingInfo(ad: Ad.Summary): StringResource? {
        return ad.parkingSpace?.takeIf { it.hasParkingSpace }?.let { parkingSpace ->
            StringResource.Resource(
                R.string.ad_parking,
                listOfNotNull(
                    StringResource.Resource(R.string.ad_included)
                        .takeIf { parkingSpace.isIncludedInPrice }
                )
            )
        }
    }

    /**
     * Retrieves additional features of the property.
     *
     * @param ad The [Ad.Summary] containing the features.
     * @return A list of [StringResource] representing the additional features.
     */
    private fun getExtraFeatures(ad: Ad.Summary): List<StringResource> {
        return listOfNotNull(
            ad.parkingSpace?.takeIf { it.hasParkingSpace }?.let {
                StringResource.Resource(
                    R.string.ad_parking,
                    if (it.isIncludedInPrice) {
                        listOf(StringResource.Resource(R.string.ad_included))
                    } else {
                        emptyList()
                    }
                )
            },
            StringResource.Resource(R.string.ad_has_boxroom).takeIf { ad.features.hasBoxRoom },
            StringResource.Resource(R.string.ad_has_garden).takeIf { ad.features.hasGarden },
            StringResource.Resource(R.string.ad_has_swimmingPool)
                .takeIf { ad.features.hasSwimmingPool },
            StringResource.Resource(R.string.ad_has_terrace).takeIf { ad.features.hasTerrace },
            StringResource.Resource(R.string.ad_has_air_conditioning)
                .takeIf { ad.features.hasAirConditioning },
        )
    }
}