package com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data Transfer Object for Multimedia (Images).
 */
@JsonClass(generateAdapter = true)
internal data class MultimediaEntity(
    @Json(name = "images") val images: List<ImageEntity>
)