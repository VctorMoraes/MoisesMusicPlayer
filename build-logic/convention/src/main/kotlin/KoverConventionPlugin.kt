import kotlinx.kover.gradle.plugin.dsl.KoverReportExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create

interface KoverExcludesExtension {
    var additionalExcludes: List<String>?
    var annotatedBy: String?
}

class KoverConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlinx.kover")
            }
            val extensions = target.extensions.create<KoverExcludesExtension>("KoverExcludes")

            afterEvaluate {
                val additionalExcludes = extensions.additionalExcludes ?: listOf()
                val annotatedBy = extensions.annotatedBy

                configure<KoverReportExtension> {
                    filters {
                        excludes {
                            classes(EXCLUDES + additionalExcludes)
                            annotatedBy?.let {
                                annotatedBy(it)
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private val EXCLUDES = listOf(
            "*_*Factory.*",
            "*_Factory.*",
            "*_Factory*",
            "*Hilt_*",
            "*_Hilt*",
        )
    }
}