import com.android.build.gradle.LibraryExtension
import com.victor.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("moisesmusicplayer.android.library")

            extensions.configure<LibraryExtension> {
                configureAndroidCompose()
            }
        }
    }
}