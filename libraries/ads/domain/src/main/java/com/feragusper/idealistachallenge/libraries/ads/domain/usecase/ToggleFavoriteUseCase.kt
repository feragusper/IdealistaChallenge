package com.feragusper.idealistachallenge.libraries.ads.domain.usecase

import com.feragusper.idealistachallenge.libraries.ads.domain.repository.AdsRepository
import javax.inject.Inject

/**
 * Toggles the favorite status of an ad.
 *
 * @property adsRepository The repository responsible for managing ads.
 */
class ToggleFavoriteUseCase @Inject constructor(
    private val adsRepository: AdsRepository
) {

    /**
     * Toggles the favorite status of an ad.
     *
     * @param adId The ID of the ad to toggle the favorite status for.
     * @return A [Result] indicating the success or failure of the operation.
     */
    suspend operator fun invoke(adId: String): Result<Unit> =
        runCatching { adsRepository.toggleFavorite(adId) }
}
