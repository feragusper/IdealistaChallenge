package com.feragusper.idealistachallenge.libraries.ads.presentation.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Utility class for formatting date-related values.
 */
object DateUtils {
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        .withZone(ZoneId.systemDefault())

    /**
     * Formats an [Instant] value into a human-readable date string.
     *
     * @param instant The [Instant] value to be formatted.
     * @return The formatted date string.
     */
    fun formatDate(instant: Instant): String = formatter.format(instant)
}
