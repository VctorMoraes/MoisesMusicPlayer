plugins {
    alias(libs.plugins.convention.feature)
}

android {
    namespace = "com.victor.feature.song_search"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common:composables"))
    implementation(project(":core:common:exceptions"))
    implementation(project(":core:common:strings"))
    implementation(project(":domain:song-search"))
    implementation(project(":data:media-player-service"))
    implementation(project(":feature:song-player"))

    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.paging.compose)

    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.lifecycle.compose.viewmodel)
}