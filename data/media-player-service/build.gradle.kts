plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.victor.data.media_player_service"
}

dependencies {
    implementation(project(":core:model"))

    api(libs.androidx.media3.exoplayer)
    api(libs.androidx.media3.session)
}