plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.data.network)
}

android {
    namespace = "com.victor.data.song_search"
}

dependencies {
    implementation(project(":core:network"))
    api(project(":core:model"))

    api(libs.androidx.paging)
}