plugins {
    alias(libs.plugins.idealista.android.feature)
    alias(libs.plugins.idealista.android.test)
}

android {
    namespace = "com.feragusper.idealistachallenge.features.detail.presentation"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":libraries:ads:domain"))
    implementation(project(":libraries:ads:presentation"))
    implementation(project(":libraries:design"))
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.material)
}