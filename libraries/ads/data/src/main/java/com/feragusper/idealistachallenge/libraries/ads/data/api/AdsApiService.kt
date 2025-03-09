package com.feragusper.idealistachallenge.libraries.ads.data.api

import com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity.AdDetailEntity
import com.feragusper.idealistachallenge.libraries.ads.data.datasource.entity.AdSummaryEntity
import retrofit2.http.GET

/**
 * Represents the API service for retrieving ads.
 */
internal interface AdsApiService {

    /**
     * Retrieves a list of ads.
     *
     * @return A list of [AdSummaryEntity] representing the ads.
     */
    @GET("list.json")
    suspend fun getAdsList(): List<AdSummaryEntity>

    /**
     * Retrieves the detail of an ad.
     *
     * @return An [AdDetailEntity] representing the ad detail.
     */
    @GET("detail.json")
    suspend fun getAdDetail(): AdDetailEntity
}
