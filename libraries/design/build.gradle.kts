plugins {
    alias(libs.plugins.idealista.android.feature)
}

android {
    namespace = "com.feragusper.idealistachallenge.libraries.design"
}

dependencies {
    implementation(libs.material)
    implementation(libs.coil.compose)
}