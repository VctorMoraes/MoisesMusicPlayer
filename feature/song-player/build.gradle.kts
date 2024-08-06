plugins {
    alias(libs.plugins.convention.feature)
}

android {
    namespace = "com.victor.feature.song_player"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":data:media-player-service"))

    implementation(libs.coil.compose)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.lifecycle.compose.viewmodel)
}