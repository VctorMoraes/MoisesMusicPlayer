plugins {
    alias(libs.plugins.convention.application)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.victor.moisesmusicplayer"

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":feature:song-search"))
    implementation(project(":data:media-player-service"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)

    //Splash
    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.navigation)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}