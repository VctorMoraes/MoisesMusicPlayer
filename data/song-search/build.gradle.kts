plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.hilt)
    alias(libs.plugins.convention.data.network)
    alias(libs.plugins.convention.kover)
}

android {
    namespace = "com.victor.data.song_search"

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {

    implementation(project(":core:network"))
    api(project(":core:model"))

    api(libs.androidx.paging)
    implementation(libs.androidx.paging.testing.android)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.serialization.json)
    testImplementation(libs.mockk)
    testImplementation(libs.okhttp.mockwebserver)
    testImplementation("app.cash.turbine:turbine:1.1.0")

}