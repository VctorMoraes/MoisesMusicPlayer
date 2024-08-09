plugins {
    alias(libs.plugins.convention.library.compose)
}

android {
    namespace = "com.victor.core.common.composables"
}

dependencies {
    implementation(libs.coil.compose)
    implementation(libs.androidx.material3)
    implementation(libs.lottie)
}