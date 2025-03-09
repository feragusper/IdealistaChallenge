package com.feragusper.idealistachallenge.libraries.ads.presentation

import com.feragusper.idealistachallenge.libraries.design.StringResource
import java.time.Instant

/**
 * UI model representing an advertisement summary.
 *
 * @property id The unique identifier of the advertisement.
 * @property images A list of image URLs associated with the advertisement.
 * @property title The title of the advertisement.
 * @property subtitle The subtitle of the advertisement.
 * @property price The price of the advertisement.
 * @property parking Information about parking availability.
 * @property size The size of the advertisement.
 * @property rooms The number of rooms in the advertisement.
 * @property extra Additional information about the advertisement.
 * @property isFavorite Indicates if the advertisement is a favorite.
 * @property favoriteDate The date when the advertisement was marked as a favorite.
 * @property operationType The type of operation associated with the advertisement.
 */
data class AdSummaryUi(
    val id: String,
    val images: List<String>,
    val title: StringResource,
    val subtitle: StringResource,
    val price: String,
    val parking: StringResource?,
    val size: StringResource,
    val rooms: StringResource,
    val extra: List<StringResource>,
    val isFavorite: Boolean,
    val favoriteDate: Instant?,
    val operationType: StringResource
)
