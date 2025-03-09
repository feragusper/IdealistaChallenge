package com.feragusper.idealistachallenge.libraries.ads.data.di

import com.feragusper.idealistachallenge.libraries.ads.data.repository.AdsRepositoryImpl
import com.feragusper.idealistachallenge.libraries.ads.domain.repository.AdsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module for providing data-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    /**
     * Binds the [AdsRepositoryImpl] to the [AdsRepository] interface.
     *
     * @param impl The implementation of the [AdsRepository] interface.
     *
     * @return An instance of the [AdsRepository] interface.
     */
    @Binds
    @Singleton
    abstract fun bindAdsRepository(
        impl: AdsRepositoryImpl
    ): AdsRepository
}
