package com.feragusper.idealistachallenge.libraries.ads.domain.model

import java.time.Instant

/**
 * Represents an ad.
 * @property id The unique identifier of the ad.
 * @property price The price of the ad.
 * @property operation The operation type of the ad (sale or rent).
 * @property propertyType The type of the property (flat).
 * @property rooms The number of rooms in the property.
 * @property bathrooms The number of bathrooms in the property.
 * @property exterior Indicates if the property has an exterior.
 * @property size The size of the property.
 * @property floor The floor of the property.
 * @property location The location of the property.
 * @property description A description of the property.
 * @property images A list of image URLs associated with the ad.
 * @property favoriteDate The date and time when the ad was marked as a favorite, or null if not marked.
 */
sealed interface Ad {

    val id: String
    val price: Price
    val operation: OperationType
    val propertyType: PropertyType
    val rooms: Int
    val bathrooms: Int
    val exterior: Boolean
    val size: Int
    val floor: String
    val location: Location
    val description: String
    val images: List<String>
    val favoriteDate: Instant?

    /**
     * Indicates if the ad is marked as a favorite.
     *
     * @return `true` if the ad is marked as a favorite, `false` otherwise.
     */
    val isFavorite
        get() = favoriteDate != null

    /**
     * Represents a summarized version of an ad, used in lists.
     *
     * @property address The address of the property.
     * @property province The province where the property is located.
     * @property district The district where the property is located.
     * @property neighborhood The neighborhood where the property is located.
     * @property parkingSpace Information about parking spaces, if available.
     * @property features Additional features of the property.
     */
    data class Summary(
        override val id: String,
        override val price: Price,
        override val propertyType: PropertyType,
        override val operation: OperationType,
        override val size: Int,
        override val rooms: Int,
        override val bathrooms: Int,
        override val exterior: Boolean,
        override val floor: String,
        override val location: Location,
        override val description: String,
        override val images: List<String>,
        override val favoriteDate: Instant?,
        val address: String,
        val province: String,
        val district: String,
        val neighborhood: String,
        val parkingSpace: ParkingSpace?,
        val features: Features,
    ) : Ad {

        /**
         * Represents information about parking spaces, if available.
         *
         * @property hasParkingSpace Indicates if the property has a parking space.
         * @property isIncludedInPrice Indicates if the parking space is included in the price.
         */
        data class ParkingSpace(
            val hasParkingSpace: Boolean,
            val isIncludedInPrice: Boolean
        )

        /**
         * Represents additional features of the property.
         *
         * @property hasSwimmingPool Indicates if the property has a swimming pool.
         * @property hasTerrace Indicates if the property has a terrace.
         * @property hasAirConditioning Indicates if the property has air conditioning.
         * @property hasBoxRoom Indicates if the property has a box room.
         * @property hasGarden Indicates if the property has a garden.
         */
        data class Features(
            val hasSwimmingPool: Boolean,
            val hasTerrace: Boolean,
            val hasAirConditioning: Boolean,
            val hasBoxRoom: Boolean,
            val hasGarden: Boolean
        )
    }

    /**
     * Represents a detailed version of an ad, used in detail views.
     *
     * @property extendedPropertyType The extended property type of the ad.
     * @property homeType The home type of the ad.
     * @property state The state of the ad.
     * @property flatLocation The location of the flat, if applicable.
     * @property characteristics Additional characteristics of the ad.
     * @property modificationDate The date and time when the ad was last modified.
     * @property energyCertification Information about the energy certification of the ad.
     */
    data class Detail(
        override val id: String,
        override val price: Price,
        override val operation: OperationType,
        override val propertyType: PropertyType,
        override val rooms: Int,
        override val size: Int,
        override val bathrooms: Int,
        override val exterior: Boolean,
        override val floor: String,
        override val location: Location,
        override val description: String,
        override val images: List<String>,
        override val favoriteDate: Instant?,
        val extendedPropertyType: String,
        val homeType: String,
        val state: String,
        val flatLocation: String?,
        val characteristics: Characteristics,
        val modificationDate: Instant,
        val energyCertification: EnergyCertification
    ) : Ad {

        /**
         * Represents additional characteristics of the ad.
         *
         * @property communityCosts The community costs of the property.
         * @property housingFurniture The type of housing furniture in the property.
         * @property agencyIsABank Indicates if the agency is a bank.
         * @property lift Indicates if the property has a lift.
         * @property boxroom Indicates if the property has a boxroom.
         * @property isDuplex Indicates if the property is a duplex.
         * @property status The status of the property.
         */
        data class Characteristics(
            val communityCosts: Double,
            val housingFurniture: String,
            val agencyIsABank: Boolean,
            val lift: Boolean,
            val boxroom: Boolean,
            val isDuplex: Boolean,
            val status: PropertyStatus
        )

        /**
         * Represents information about the energy certification of the ad.
         *
         * @property title The title of the energy certification.
         * @property energyConsumption The energy consumption of the property.
         * @property emissions The emissions associated with the energy certification.
         */
        data class EnergyCertification(
            val title: String,
            val energyConsumption: String,
            val emissions: String
        )
    }

    /**
     * Represents a Location
     *
     * @property latitude The latitude of the location.
     * @property longitude The longitude of the location.
     */
    data class Location(
        val latitude: Double,
        val longitude: Double,
    )

    /**
     * Represents a Property Type
     *
     * @property value The value of the property type.
     */
    enum class PropertyType(val value: String) {
        FLAT("flat"),
    }

    /**
     * Represents an Operation Type
     *
     * @property value The value of the operation type.
     */
    enum class OperationType(val value: String) {
        SALE("sale"),
        RENT("rent")
    }

    /**
     * Represents a Property Status
     *
     * @property value The value of the property status.
     */
    enum class PropertyStatus(val value: String) {
        RENEWED("renew"),
    }
}