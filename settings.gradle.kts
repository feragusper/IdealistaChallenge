// =======================================
// Plugin Management Configuration
// =======================================
pluginManagement {
    // Include custom build logic from the 'build-logic' module
    includeBuild("build-logic")

    // Define repositories for resolving plugins
    repositories {
        google()             // Google's Maven repository (Android dependencies)
        mavenCentral()        // Central Maven repository for Java/Kotlin libraries
        gradlePluginPortal()  // Gradle's official plugin repository
    }
}

// =======================================
// Dependency Resolution Management
// =======================================
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    // Prevents project-level repositories from being used,
    // enforcing dependency resolution through central repositories only.
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    // Define global repositories for dependency resolution
    repositories {
        google()             // Google's Maven repository
        mavenCentral()        // Central Maven repository
    }
}

rootProject.name = "IdealistaChallenge"
include(":app")
include(":libraries:design")
