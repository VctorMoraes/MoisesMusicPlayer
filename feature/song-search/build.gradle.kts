plugins {
    alias(libs.plugins.convention.feature)
}

android {
    namespace = "com.victor.feature.song_search"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":domain:song-search"))
    implementation(project(":data:media-player-service"))

    implementation(libs.coil.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.paging.compose)

    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.lifecycle.compose.viewmodel)

    implementation("androidx.media3:media3-exoplayer:1.4.0")
    implementation("androidx.media3:media3-exoplayer-dash:1.4.0")
}