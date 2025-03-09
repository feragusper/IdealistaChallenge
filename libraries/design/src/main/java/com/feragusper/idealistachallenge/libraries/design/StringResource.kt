package com.feragusper.idealistachallenge.libraries.design

import android.content.Context
import androidx.annotation.StringRes

/**
 * Represents a resource for a string that might either be directly provided as text (Raw)
 * or referenced by a resource ID (Resource) possibly with formatting arguments.
 * This sealed class allows for a flexible representation of text resources within the app,
 * supporting both static text and text fetched from Android's string resources with arguments for formatting.
 */
sealed class StringResource {

    /**
     * Formats the string resource based on its type (Resource or Raw).
     * For [Resource], it fetches and formats the string from Android's resources.
     * For [Raw], it simply returns the raw text.
     *
     * @param context The context used to access resources.
     * @return The formatted string.
     */
    @Suppress("SpreadOperator")
    fun format(context: Context): String =
        when (this) {
            is Resource -> {
                val formattedArgs = args.map { arg ->
                    when (arg) {
                        is StringResource -> arg.format(context)
                        else -> arg
                    }
                }.toTypedArray()

                context.getString(resId, *formattedArgs)
            }

            is Raw -> {
                text
            }
        }

    /**
     * Represents a string resource that is identified by a resource ID.
     * This allows for localization and dynamic argument replacement at runtime.
     *
     * @property resId The resource ID of the string.
     * @property args Optional arguments for formatting the string resource.
     */
    data class Resource(
        @StringRes val resId: Int,
        val args: List<Any> = emptyList(),
    ) : StringResource()

    /**
     * Represents a string resource provided as raw text.
     *
     * @property text The raw text of the resource.
     */
    data class Raw(val text: String) : StringResource()
}