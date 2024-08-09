plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.hilt)
}

android {
    namespace = "com.victor.core.network"
}

dependencies {
    api(project(":core:common:exceptions"))

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlinx.serialization)
}