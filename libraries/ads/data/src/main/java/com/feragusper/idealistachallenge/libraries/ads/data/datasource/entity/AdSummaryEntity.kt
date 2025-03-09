package com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity

import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data Transfer Object for Ad Summary.
 */
@JsonClass(generateAdapter = true)
internal data class AdSummaryEntity(
    @Json(name = "propertyCode") val id: String,
    @Json(name = "floor") val floor: String,
    @Json(name = "priceInfo") val priceInfo: PriceInfoWrapperEntity,
    @Json(name = "propertyType") val propertyType: String,
    @Json(name = "operation") val operation: String,
    @Json(name = "size") val size: Double,
    @Json(name = "exterior") val exterior: Boolean,
    @Json(name = "rooms") val rooms: Int,
    @Json(name = "bathrooms") val bathrooms: Int,
    @Json(name = "address") val address: String,
    @Json(name = "province") val province: String,
    @Json(name = "district") val district: String,
    @Json(name = "neighborhood") val neighborhood: String,
    @Json(name = "latitude") val latitude: Double,
    @Json(name = "longitude") val longitude: Double,
    @Json(name = "description") val description: String,
    @Json(name = "multimedia") val multimedia: MultimediaEntity,
    @Json(name = "parkingSpace") val parkingSpace: ParkingEntity?,
    @Json(name = "features") val features: FeaturesEntity
) {
    @JsonClass(generateAdapter = true)
    data class PriceInfoWrapperEntity(
        @Json(name = "price") val price: PriceInfoEntity,
    )

    /**
     * Data Transfer Object for Features.
     */
    @JsonClass(generateAdapter = true)
    data class FeaturesEntity(
        @Json(name = "hasSwimmingPool") val hasSwimmingPool: Boolean?,
        @Json(name = "hasTerrace") val hasTerrace: Boolean?,
        @Json(name = "hasAirConditioning") val hasAirConditioning: Boolean?,
        @Json(name = "hasBoxRoom") val hasBoxRoom: Boolean?,
        @Json(name = "hasGarden") val hasGarden: Boolean?
    )

    /**
     * Data Transfer Object for Parking Information.
     */
    @JsonClass(generateAdapter = true)
    data class ParkingEntity(
        @Json(name = "hasParkingSpace") val hasParkingSpace: Boolean,
        @Json(name = "isParkingSpaceIncludedInPrice") val isParkingSpaceIncludedInPrice: Boolean
    ) {
        fun toParkingSpace(): Ad.Summary.ParkingSpace {
            return Ad.Summary.ParkingSpace(
                hasParkingSpace = hasParkingSpace,
                isIncludedInPrice = isParkingSpaceIncludedInPrice
            )
        }

    }
}