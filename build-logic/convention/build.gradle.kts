import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.victor.moisesmusicplayer.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xcontext-receivers")
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.kover.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "moisesmusicplayer.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidFeature") {
            id = "moisesmusicplayer.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidHilt") {
            id = "moisesmusicplayer.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("kover") {
            id = "moisesmusicplayer.android.kover"
            implementationClass = "KoverConventionPlugin"
        }
        register("androidLibrary") {
            id = "moisesmusicplayer.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "moisesmusicplayer.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidDataNetwork") {
            id = "moisesmusicplayer.android.data.network"
            implementationClass = "AndroidNetworkConventionPlugin"
        }
    }
}