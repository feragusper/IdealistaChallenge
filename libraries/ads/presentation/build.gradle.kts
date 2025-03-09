plugins {
    alias(libs.plugins.idealista.android.feature)
    alias(libs.plugins.idealista.android.test)
}

android {
    namespace = "com.feragusper.idealistachallenge.libraries.ads.presentation"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":libraries:ads:domain"))
    implementation(project(":libraries:design"))
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.cardview)
    implementation(libs.material)
}