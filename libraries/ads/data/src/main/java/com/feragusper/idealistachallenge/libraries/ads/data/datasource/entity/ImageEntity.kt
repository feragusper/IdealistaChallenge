package com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ImageEntity(
    @Json(name = "url") val url: String?,
    @Json(name = "tag") val tag: String?,
    @Json(name = "localizedName") val localizedName: String?,
    @Json(name = "multimediaId") val multimediaId: Long?
)