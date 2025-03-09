package com.feragusper.idealistachallenge

import android.app.Activity
import com.feragusper.idealistachallenge.libraries.navigation.NavigationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * Dagger module to provide the [NavigationProvider] dependency for activities.
 */
@Module
@InstallIn(ActivityComponent::class)
object NavigationModule {

    /**
     * Provides a [NavigationProvider] instance for activities.
     *
     * @param activity The current activity where navigation is handled.
     * @return The activity cast as [NavigationProvider].
     * @throws IllegalArgumentException if the activity does not implement [NavigationProvider].
     */
    @Provides
    fun provideNavigationProvider(activity: Activity): NavigationProvider {
        require(activity is NavigationProvider) {
            "Activity must implement NavigationProvider"
        }
        return activity
    }
}
