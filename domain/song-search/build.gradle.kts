plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.victor.domain.song_search"
}

dependencies {
    implementation(project(":data:song-search"))
}