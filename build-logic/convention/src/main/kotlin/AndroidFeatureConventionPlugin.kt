import com.android.build.gradle.LibraryExtension
import com.victor.convention.androidTestImplementation
import com.victor.convention.configureAndroidCompose
import com.victor.convention.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("moisesmusicplayer.android.library")
            pluginManager.apply("moisesmusicplayer.android.hilt")

            dependencies {
                add("androidTestImplementation", kotlin("test"))
                androidTestImplementation("androidx.junit")
            }

            extensions.configure<LibraryExtension> {
                configureAndroidCompose()
            }
        }
    }
}