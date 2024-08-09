plugins {
    alias(libs.plugins.convention.library)
}

android {
    namespace = "com.victor.core.model"

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}