package com.feragusper.idealistachallenge.libraries.ads.data.di

import com.feragusper.idealistachallenge.libraries.ads.data.api.AdsApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Module for providing network-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    /**
     * Provides an instance of [OkHttpClient] for making network requests.
     *
     * @return An instance of [OkHttpClient].
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    /**
     * Provides an instance of [Moshi] for JSON parsing.
     *
     * @return An instance of [Moshi].
     */
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    /**
     * Provides an instance of [Retrofit] for making network requests.
     *
     * @param okHttpClient The [OkHttpClient] instance.
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://idealista.github.io/android-challenge/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    /**
     * Provides an instance of [AdsApiService] for making network requests.
     *
     * @param retrofit The [Retrofit] instance.
     */
    @Provides
    @Singleton
    fun provideAdsApiService(retrofit: Retrofit): AdsApiService {
        return retrofit.create(AdsApiService::class.java)
    }
}
