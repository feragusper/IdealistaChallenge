package com.feragusper.idealistachallenge.features.detail.presentation.ui

import com.feragusper.idealistachallenge.libraries.design.StringResource

/**
 * Represents the UI state for displaying ad details.
 *
 * @property id The unique identifier of the ad.
 * @property images A list of image URLs associated with the ad.
 * @property title The title of the ad.
 * @property subtitle The subtitle of the ad.
 * @property price The price of the ad.
 * @property homeState The state of the home associated with the ad.
 * @property size The size of the ad.
 * @property rooms The number of rooms in the ad.
 * @property bathrooms The number of bathrooms in the ad.
 * @property description The description of the ad.
 * @property characteristics The characteristics of the ad.
 * @property building The building information of the ad.
 * @property energyCertification The energy certification information of the ad.
 * @property isFavorite Indicates if the ad is a favorite.
 * @property favoriteDate The date when the ad was marked as a favorite.
 * @property totalPrice The total price of the ad.
 * @property pricePerSquareMeter The price per square meter of the ad.
 * @property modificationDate The date when the ad was last modified.
 */
data class AdDetailUi(
    val id: String,
    val images: List<String>,
    val title: StringResource,
    val subtitle: StringResource,
    val price: String,
    val homeState: String,
    val size: StringResource,
    val rooms: StringResource,
    val bathrooms: StringResource,
    val description: String,
    val characteristics: StringResource,
    val building: StringResource,
    val energyCertification: StringResource,
    val isFavorite: Boolean,
    val favoriteDate: StringResource?,
    val totalPrice: String,
    val pricePerSquareMeter: StringResource,
    val modificationDate: StringResource
)