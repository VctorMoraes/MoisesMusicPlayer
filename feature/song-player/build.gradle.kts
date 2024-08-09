
plugins {
    alias(libs.plugins.convention.feature)
}

android {
    namespace = "com.victor.feature.song_player"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common:composables"))
    implementation(project(":core:common:strings"))
    implementation(project(":data:media-player-service"))
    implementation(project(":domain:album-search"))

    implementation(libs.coil.compose)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.lifecycle.compose.viewmodel)

    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
}