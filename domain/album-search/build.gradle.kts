plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.victor.domain.album_search"
}

dependencies {
    implementation(project(":data:song-search"))
}