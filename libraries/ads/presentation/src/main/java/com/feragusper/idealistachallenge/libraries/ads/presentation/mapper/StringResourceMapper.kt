package com.feragusper.idealistachallenge.libraries.ads.presentation.mapper

import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import com.feragusper.idealistachallenge.libraries.ads.presentation.R
import com.feragusper.idealistachallenge.libraries.design.StringResource

/**
 * Provides mappings from domain models to UI-friendly string resources.
 */
object StringResourceMapper {

    /**
     * Maps a [Ad.PropertyType] to a [StringResource].
     *
     * @param type The [Ad.PropertyType] to be mapped.
     * @return The corresponding [StringResource].
     */
    fun mapPropertyType(type: Ad.PropertyType): StringResource = when (type) {
        Ad.PropertyType.FLAT -> StringResource.Resource(R.string.ad_apartment)
    }

    /**
     * Maps an [Ad.OperationType] to a [StringResource].
     *
     * @param type The [Ad.OperationType] to be mapped.
     * @return The corresponding [StringResource].
     */
    fun mapOperationType(type: Ad.OperationType): StringResource = StringResource.Resource(
        when (type) {
            Ad.OperationType.SALE -> R.string.ad_sale
            Ad.OperationType.RENT -> R.string.ad_rent
        }
    )

    /**
     * Maps a [Ad.PropertyStatus] to a [StringResource].
     *
     * @param status The [Ad.PropertyStatus] to be mapped.
     * @return The corresponding [StringResource].
     */
    fun mapPropertyStatus(status: Ad.PropertyStatus): StringResource = when (status) {
        Ad.PropertyStatus.RENEWED -> StringResource.Resource(R.string.ad_renewed)
    }
}
