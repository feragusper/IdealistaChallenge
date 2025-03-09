package com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Detail entity for an ad.
 */
@JsonClass(generateAdapter = true)
internal data class AdDetailEntity(
    @Json(name = "adid") val id: Int,
    @Json(name = "priceInfo") val priceInfo: PriceInfoEntity,
    @Json(name = "operation") val operation: String,
    @Json(name = "propertyType") val propertyType: String,
    @Json(name = "extendedPropertyType") val extendedPropertyType: String,
    @Json(name = "homeType") val homeType: String,
    @Json(name = "state") val state: String,
    @Json(name = "multimedia") val multimedia: MultimediaEntity,
    @Json(name = "propertyComment") val propertyComment: String,
    @Json(name = "ubication") val location: LocationEntity,
    @Json(name = "moreCharacteristics") val characteristics: CharacteristicsEntity,
    @Json(name = "energyCertification") val energyCertification: EnergyCertificationEntity
) {
    /**
     * Characteristics entity for an ad.
     */
    @JsonClass(generateAdapter = true)
    data class CharacteristicsEntity(
        @Json(name = "communityCosts") val communityCosts: Double,
        @Json(name = "roomNumber") val roomNumber: Int,
        @Json(name = "bathNumber") val bathNumber: Int,
        @Json(name = "exterior") val exterior: Boolean,
        @Json(name = "housingFurnitures") val housingFurnitures: String,
        @Json(name = "agencyIsABank") val agencyIsABank: Boolean,
        @Json(name = "energyCertificationType") val energyCertificationType: String,
        @Json(name = "flatLocation") val flatLocation: String,
        @Json(name = "modificationDate") val modificationDate: Long,
        @Json(name = "constructedArea") val constructedArea: Int,
        @Json(name = "lift") val lift: Boolean,
        @Json(name = "boxroom") val boxroom: Boolean,
        @Json(name = "isDuplex") val isDuplex: Boolean,
        @Json(name = "floor") val floor: String,
        @Json(name = "status") val status: String
    )

    /**
     * Energy certification entity for an ad.
     */
    @JsonClass(generateAdapter = true)
    data class EnergyCertificationEntity(
        @Json(name = "title") val title: String,
        @Json(name = "energyConsumption") val energyConsumption: EnergyTypeEntity,
        @Json(name = "emissions") val emissions: EnergyTypeEntity
    ) {
        @JsonClass(generateAdapter = true)
        data class EnergyTypeEntity(
            @Json(name = "type") val type: String
        )
    }

    /**
     * Multimedia entity for an ad.
     */
    @JsonClass(generateAdapter = true)
    data class LocationEntity(
        @Json(name = "latitude") val latitude: Double,
        @Json(name = "longitude") val longitude: Double
    )
}
