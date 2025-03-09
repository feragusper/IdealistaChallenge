package com.feragusper.idealistachallenge.libraries.ads.presentation.utils

import com.feragusper.idealistachallenge.libraries.ads.domain.model.Price
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.text.NumberFormat
import java.util.Locale

class PriceUtilsTest {

    @Nested
    @DisplayName("GIVEN a price")
    inner class FormatPriceTests {

        @Test
        fun `WHEN formatting a price THEN returns correct string`() {
            // GIVEN
            val price = Price(amount = 250000.0, currencySuffix = "EUR")
            val expectedFormattedPrice = "${
                NumberFormat.getNumberInstance(Locale("es", "ES")).format(price.amount)
            } ${price.currencySuffix}"

            // WHEN
            val result = PriceUtils.formatPrice(price)

            // THEN
            result shouldBe expectedFormattedPrice
        }
    }
}
