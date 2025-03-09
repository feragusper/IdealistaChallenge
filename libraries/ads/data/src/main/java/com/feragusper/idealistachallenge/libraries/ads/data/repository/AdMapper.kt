package com.feragusper.idealistachallenge.libraries.ads.data.repository

import com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity.AdDetailEntity
import com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity.AdSummaryEntity
import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import java.time.Instant
import javax.inject.Inject

/**
 * Mapper class for mapping [AdSummaryEntity] to [Ad.Summary] and [AdDetailEntity] to [Ad.Detail].
 */
internal class AdMapper @Inject constructor() {

    /**
     * Maps an [AdSummaryEntity] to an [Ad.Summary].
     */
    fun mapSummary(entity: AdSummaryEntity): Ad.Summary = Ad.Summary(
        id = entity.id,
        price = entity.priceInfo.price.toPrice(),
        operation = entity.operation.toOperationType(),
        propertyType = entity.propertyType.toPropertyType(),
        rooms = entity.rooms,
        bathrooms = entity.bathrooms,
        size = entity.size.toInt(),
        favoriteDate = null,
        parkingSpace = entity.parkingSpace?.toParkingSpace(),
        province = entity.province,
        district = entity.district,
        neighborhood = entity.neighborhood,
        floor = entity.floor,
        location = entity.toLocation(),
        description = entity.description,
        exterior = entity.exterior,
        address = entity.address,
        images = entity.multimedia.images.mapNotNull { it.url },
        features = entity.toFeatures()
    )

    /**
     * Maps an [AdDetailEntity] to an [Ad.Detail].
     */
    fun mapDetail(entity: AdDetailEntity): Ad.Detail = Ad.Detail(
        id = entity.id.toString(),
        price = entity.priceInfo.toPrice(),
        operation = entity.operation.toOperationType(),
        propertyType = entity.homeType.toPropertyType(),
        extendedPropertyType = entity.extendedPropertyType,
        homeType = entity.homeType,
        state = entity.state,
        images = entity.multimedia.images.mapNotNull { it.url },
        description = entity.propertyComment,
        rooms = entity.characteristics.roomNumber,
        bathrooms = entity.characteristics.bathNumber,
        size = entity.characteristics.constructedArea,
        favoriteDate = null,
        flatLocation = entity.characteristics.flatLocation,
        floor = entity.characteristics.floor,
        exterior = entity.characteristics.exterior,
        location = entity.toLocation(),
        characteristics = entity.toCharacteristics(),
        modificationDate = Instant.ofEpochMilli(entity.characteristics.modificationDate),
        energyCertification = entity.toEnergyCertification()
    )

    // ------------------------
    // 🛠 Helpers for Mapping
    // ------------------------

    private fun AdSummaryEntity.toLocation() = Ad.Location(latitude, longitude)

    private fun AdSummaryEntity.toFeatures() = Ad.Summary.Features(
        hasSwimmingPool = features.hasSwimmingPool ?: false,
        hasTerrace = features.hasTerrace ?: false,
        hasAirConditioning = features.hasAirConditioning ?: false,
        hasBoxRoom = features.hasBoxRoom ?: false,
        hasGarden = features.hasGarden ?: false
    )

    private fun AdDetailEntity.toLocation() = Ad.Location(location.latitude, location.longitude)

    private fun AdDetailEntity.toCharacteristics() = Ad.Detail.Characteristics(
        communityCosts = characteristics.communityCosts,
        housingFurniture = characteristics.housingFurnitures,
        agencyIsABank = characteristics.agencyIsABank,
        lift = characteristics.lift,
        boxroom = characteristics.boxroom,
        isDuplex = characteristics.isDuplex,
        status = characteristics.status.toPropertyStatus()
    )

    private fun AdDetailEntity.toEnergyCertification() = Ad.Detail.EnergyCertification(
        title = energyCertification.title,
        emissions = energyCertification.emissions.type,
        energyConsumption = energyCertification.energyConsumption.type
    )

    // ------------------------
    // 🔹 Enum Mapping Helpers
    // ------------------------

    private fun String.toPropertyType(): Ad.PropertyType = when (this) {
        Ad.PropertyType.FLAT.value -> Ad.PropertyType.FLAT
        else -> throw IllegalArgumentException("Unknown PropertyType: $this")
    }

    private fun String.toOperationType(): Ad.OperationType = when (this) {
        Ad.OperationType.SALE.value -> Ad.OperationType.SALE
        Ad.OperationType.RENT.value -> Ad.OperationType.RENT
        else -> throw IllegalArgumentException("Unknown OperationType: $this")
    }

    private fun String.toPropertyStatus(): Ad.PropertyStatus = when (this) {
        Ad.PropertyStatus.RENEWED.value -> Ad.PropertyStatus.RENEWED
        else -> throw IllegalArgumentException("Unknown PropertyStatus: $this")
    }
}