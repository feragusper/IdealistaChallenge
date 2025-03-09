package com.feragusper.idealistachallenge.libraries.ads.domain.repository

import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing ads.
 */
interface AdsRepository {

    /**
     * Retrieves a list of ads.
     *
     * @return A [Flow] emitting a list of [Ad.Summary].
     */
    fun getAdsList(): Flow<List<Ad.Summary>>

    /**
     * Retrieves a list of favorite ads.
     *
     * @return A [Flow] emitting a list of [Ad.Summary] representing favorite ads.
     */
    fun getFavoriteAdsList(): Flow<List<Ad.Summary>>

    /**
     * Retrieves the detail of an ad.
     *
     * @return A [Flow] emitting the [Ad.Detail] representing the ad detail.
     */
    fun getAdDetail(): Flow<Ad.Detail>

    /**
     * Toggles the favorite status of an ad.
     */
    suspend fun toggleFavorite(adId: String)
}
