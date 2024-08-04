import com.android.build.gradle.LibraryExtension
import com.victor.convention.configureKotlinAndroid
import com.victor.convention.implementation
import com.victor.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidNetworkConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                implementation("retrofit.core")
                implementation("retrofit.kotlinx.serialization")
            }
        }
    }
}