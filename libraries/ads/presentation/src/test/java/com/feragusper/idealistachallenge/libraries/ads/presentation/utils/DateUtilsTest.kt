package com.feragusper.idealistachallenge.libraries.ads.presentation.utils

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DateUtilsTest {

    @Nested
    @DisplayName("GIVEN an Instant")
    inner class FormatDateTests {

        @Test
        fun `WHEN formatting a fixed date THEN returns correct string`() {
            // GIVEN
            val fixedInstant = Instant.parse("2024-03-01T12:00:00Z") // Fecha fija para testing
            val expectedFormattedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                .withZone(ZoneId.systemDefault())
                .format(fixedInstant)

            // WHEN
            val result = DateUtils.formatDate(fixedInstant)

            // THEN
            result shouldBe expectedFormattedDate
        }
    }
}
