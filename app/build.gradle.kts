plugins {
    alias(libs.plugins.idealista.android.application)
}

android {
    namespace = "com.feragusper.idealistachallenge"

    defaultConfig {
        applicationId = "com.feragusper.idealistachallenge"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":libraries:design"))
    implementation(project(":libraries:ads:data"))
    implementation(project(":libraries:navigation"))
    implementation(project(":features:ads:list"))
    implementation(project(":features:ads:favorites"))
    implementation(project(":features:ads:detail"))
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
}