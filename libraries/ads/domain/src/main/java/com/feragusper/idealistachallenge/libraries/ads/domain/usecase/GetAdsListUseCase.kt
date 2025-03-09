package com.feragusper.idealistachallenge.libraries.ads.domain.usecase

import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import com.feragusper.idealistachallenge.libraries.ads.domain.repository.AdsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Retrieves a list of ads.
 *
 * @property adsRepository The repository responsible for managing ads.
 */
class GetAdsListUseCase @Inject constructor(
    private val adsRepository: AdsRepository
) {

    /**
     * Retrieves a list of ads.
     *
     * @return A [Flow] emitting a [Result] containing the list of [Ad.Summary] if successful, or an error if it fails.
     */
    operator fun invoke(): Flow<Result<List<Ad.Summary>>> =
        adsRepository.getAdsList()
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }
}
