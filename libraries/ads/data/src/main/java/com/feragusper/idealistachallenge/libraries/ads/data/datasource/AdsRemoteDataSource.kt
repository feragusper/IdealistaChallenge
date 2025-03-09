package com.feragusper.idealistachallenge.libraries.ads.data.datasource

import com.feragusper.idealistachallenge.libraries.ads.data.api.AdsApiService
import com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity.AdDetailEntity
import com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity.AdSummaryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Represents a remote data source for retrieving ads.
 *
 * @property apiService The API service for retrieving ads.
 */
internal class AdsRemoteDataSource @Inject constructor(
    private val apiService: AdsApiService
) {

    /**
     * Retrieves a list of ads.
     *
     * @return A [Flow] emitting a list of [AdSummaryEntity] representing the ads.
     */
    fun getAdsList(): Flow<List<AdSummaryEntity>> = flow {
        emit(apiService.getAdsList())
    }

    /**
     * Retrieves the detail of an ad.
     *
     * @return A [Flow] emitting an [AdDetailEntity] representing the ad detail.
     */
    fun getAdDetail(): Flow<AdDetailEntity> = flow {
        emit(apiService.getAdDetail())
    }
}
