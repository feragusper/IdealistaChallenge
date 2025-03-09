package com.feragusper.idealistachallenge

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.feragusper.idealistachallenge.databinding.ActivityMainBinding
import com.feragusper.idealistachallenge.libraries.navigation.NavigationProvider
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity that manages navigation between the ads list, favorites, and detail screens.
 * Uses Hilt for dependency injection.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationProvider {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupNavigation()
    }

    /**
     * Configures the toolbar as the action bar.
     */
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    /**
     * Initializes the navigation controller and sets up the bottom navigation.
     */
    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_ads, R.id.nav_favorites)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.subtitle = ""
            binding.bottomNavigationView.visibility =
                if (destination.id == R.id.detailFragment) View.GONE else View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun navigateToDetail() {
        navController.navigate(R.id.action_global_detailFragment)
    }
}