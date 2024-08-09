import com.android.build.api.dsl.ApplicationExtension
import com.victor.convention.MoisesMusicPlayerBuildType
import com.victor.convention.configureAndroidCompose
import com.victor.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }



            extensions.configure<ApplicationExtension> {
                compileSdk = 34

                defaultConfig {
                    applicationId = "com.victor.moisesmusicplayer"
                    minSdk = 27
                    targetSdk = 34
                    versionCode = 1
                    versionName = "1.0"

                    multiDexEnabled = true

//                    testInstrumentationRunner = "com.victor.moisesmusicplayer.HiltTestRunner"
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                buildTypes {
                    debug {
                        applicationIdSuffix = MoisesMusicPlayerBuildType.DEBUG.applicationIdSuffix
                    }
                    release {
                        isMinifyEnabled = false
                        applicationIdSuffix = MoisesMusicPlayerBuildType.RELEASE.applicationIdSuffix
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }

                configureKotlinAndroid()
                configureAndroidCompose()
            }
        }
    }
}