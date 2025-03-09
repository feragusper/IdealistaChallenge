import com.android.build.gradle.LibraryExtension
import com.feragusper.idealistachallenge.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

/**
 * Convention plugin to configure Android library modules with standardized settings and dependencies.
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply essential plugins for an Android library module
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.android")

            // Configure common Android settings for library modules
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this) // Apply common Kotlin Android settings

                defaultConfig.targetSdk = 35 // Target SDK version
            }
        }
    }
}
