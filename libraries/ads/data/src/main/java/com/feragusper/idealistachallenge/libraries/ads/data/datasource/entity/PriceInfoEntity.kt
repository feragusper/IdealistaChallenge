package com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity

import com.feragusper.idealistachallenge.libraries.ads.domain.model.Price
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data Transfer Object for Price Information.
 */
@JsonClass(generateAdapter = true)
internal data class PriceInfoEntity(
    @Json(name = "amount") val amount: Double,
    @Json(name = "currencySuffix") val currencySuffix: String
) {
    fun toPrice(): Price {
        return Price(
            amount = amount,
            currencySuffix = currencySuffix
        )
    }
}