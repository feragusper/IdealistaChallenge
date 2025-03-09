package com.feragusper.idealistachallenge.libraries.ads.domain.usecase

import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import com.feragusper.idealistachallenge.libraries.ads.domain.repository.AdsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Retrieves the details of a specific ad.
 *
 * @property adsRepository The repository responsible for managing ads.
 */
class GetAdDetailUseCase @Inject constructor(
    private val adsRepository: AdsRepository
) {

    /**
     * Retrieves the details of a specific ad.
     *
     * @return A [Flow] emitting a [Result] containing the [Ad.Detail] if successful, or an error if it fails.
     */
    operator fun invoke(): Flow<Result<Ad.Detail>> =
        adsRepository.getAdDetail()
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }
}