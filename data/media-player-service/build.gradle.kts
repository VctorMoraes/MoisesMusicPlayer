plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.victor.data.media_player_service"
}

dependencies {
    api("androidx.media3:media3-exoplayer:1.4.0")
    api("androidx.media3:media3-session:1.4.0")
}