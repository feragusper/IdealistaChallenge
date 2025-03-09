plugins {
    alias(libs.plugins.idealista.jvm.library)
    alias(libs.plugins.idealista.android.test)
}

dependencies {
    implementation(libs.bundles.coroutines)
}