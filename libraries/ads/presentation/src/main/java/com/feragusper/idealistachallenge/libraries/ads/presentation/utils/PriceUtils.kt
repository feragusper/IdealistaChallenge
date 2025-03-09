package com.feragusper.idealistachallenge.libraries.ads.presentation.utils

import com.feragusper.idealistachallenge.libraries.ads.domain.model.Price
import java.text.NumberFormat
import java.util.Locale

/**
 * Utility class for formatting price-related values.
 */
object PriceUtils {
    private val formatter = NumberFormat.getNumberInstance(Locale("es", "ES"))

    /**
     * Formats a [Price] value into a human-readable price string.
     *
     * @param price The [Price] value to be formatted.
     * @return The formatted price string.
     */
    fun formatPrice(price: Price): String =
        "${formatter.format(price.amount)} ${price.currencySuffix}"
}
