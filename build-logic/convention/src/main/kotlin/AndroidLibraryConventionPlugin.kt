import com.android.build.gradle.LibraryExtension
import com.victor.convention.configureKotlinAndroid
import com.victor.convention.implementation
import com.victor.convention.libs
import com.victor.convention.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                defaultConfig.targetSdk = 34
                defaultConfig.minSdk = 27
                defaultConfig.multiDexEnabled = true
                configureKotlinAndroid()
            }

            dependencies {
                add("testImplementation", kotlin("test"))
            }
        }
    }
}