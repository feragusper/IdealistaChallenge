plugins {
    alias(libs.plugins.idealista.android.library)
    alias(libs.plugins.idealista.hilt)
    alias(libs.plugins.idealista.android.test)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.feragusper.idealistachallenge.libraries.ride.data"
}

dependencies {
    implementation(project(":libraries:ads:domain"))
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.datastore.preferences)

}