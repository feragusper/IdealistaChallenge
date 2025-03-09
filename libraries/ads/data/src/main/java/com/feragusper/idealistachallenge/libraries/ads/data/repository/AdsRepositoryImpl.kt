package com.feragusper.idealistachallenge.libraries.ads.data.repository

import com.feragusper.idealistachallenge.libraries.ads.data.datasource.AdsRemoteDataSource
import com.feragusper.idealistachallenge.libraries.ads.data.datasource.FavoriteAdsDataSource
import com.feragusper.idealistachallenge.libraries.ads.domain.model.Ad
import com.feragusper.idealistachallenge.libraries.ads.domain.repository.AdsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject

/**
 * Implementation of the [AdsRepository] interface.
 *
 * @param remoteDataSource The data source for remote data.
 * @param favoriteAdsDataSource The data source for favorite ads.
 * @param adMapper The mapper for mapping data entities to domain models.
 */
internal class AdsRepositoryImpl @Inject constructor(
    private val remoteDataSource: AdsRemoteDataSource,
    private val favoriteAdsDataSource: FavoriteAdsDataSource,
    private val adMapper: AdMapper
) : AdsRepository {

    /**
     * Retrieves a list of ads.
     *
     * @return A flow of lists of [Ad.Summary].
     */
    override fun getAdsList(): Flow<List<Ad.Summary>> =
        remoteDataSource.getAdsList()
            .map { summaries -> summaries.map { adMapper.mapSummary(it) } }
            .combine(favoriteAdsDataSource.getFavorites()) { summaries, favoriteMap ->
                summaries.map { ad ->
                    ad.copy(
                        favoriteDate = favoriteMap[ad.id]?.let { Instant.ofEpochMilli(it) }
                    )
                }
            }

    /**
     * Retrieves a list of favorite ads.
     *
     * @return A flow of lists of [Ad.Summary].
     */
    override fun getFavoriteAdsList(): Flow<List<Ad.Summary>> =
        getAdsList().map { ads -> ads.filter { it.isFavorite } }

    /**
     * Retrieves the ad detail.
     *
     * @return A flow of the [Ad.Detail].
     */
    override fun getAdDetail(): Flow<Ad.Detail> =
        remoteDataSource.getAdDetail().flatMapLatest { entity ->
            val adDetail = adMapper.mapDetail(entity)

            favoriteAdsDataSource.getFavorite(entity.id.toString())
                .map { favoriteDate ->
                    adDetail.copy(favoriteDate = favoriteDate?.let { Instant.ofEpochMilli(it) })
                }
        }

    /**
     * Toggles the favorite status of an ad.
     *
     * @param adId The ID of the ad.
     */
    override suspend fun toggleFavorite(adId: String) {
        val isFavorite = favoriteAdsDataSource.isFavorite(adId).first()
        if (isFavorite) {
            favoriteAdsDataSource.removeFavorite(adId)
        } else {
            favoriteAdsDataSource.addFavorite(adId, Instant.now().toEpochMilli())
        }
    }
}
