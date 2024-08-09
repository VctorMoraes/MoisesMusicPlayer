import com.victor.convention.implementation
import com.victor.convention.kapt
import com.victor.convention.kaptAndroidTest
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.dagger.hilt.android")
                // KAPT must go last to avoid build warnings.
                // See: https://stackoverflow.com/questions/70550883/warning-the-following-options-were-not-recognized-by-any-processor-dagger-f
                apply("org.jetbrains.kotlin.kapt")
            }

            dependencies {
                implementation("hilt")
                kapt("hilt.compiler")
                implementation("hilt.navigation")
                kaptAndroidTest("hilt.compiler")
            }
        }
    }
}