plugins {
    alias(libs.plugins.idealista.android.feature)
    alias(libs.plugins.idealista.android.test)
}

android {
    namespace = "com.feragusper.idealistachallenge.features.ads"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":libraries:ads:domain"))
    implementation(project(":libraries:ads:presentation"))
    implementation(project(":libraries:design"))
    implementation(project(":libraries:navigation"))
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.material)
}