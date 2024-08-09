package com.victor.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

context(Project)
fun DependencyHandlerScope.implementation(versionCatalogAlias: String) {
    val configurationName = object {}.javaClass.enclosingMethod.name
    add(configurationName, libs.findLibrary(versionCatalogAlias).get())
}

context(Project)
fun DependencyHandlerScope.kapt(versionCatalogAlias: String) {
    val configurationName = object {}.javaClass.enclosingMethod.name
    add(configurationName, libs.findLibrary(versionCatalogAlias).get())
}

context(Project)
fun DependencyHandlerScope.kaptAndroidTest(versionCatalogAlias: String) {
    val configurationName = object {}.javaClass.enclosingMethod.name
    add(configurationName, libs.findLibrary(versionCatalogAlias).get())
}

context(Project)
fun DependencyHandlerScope.testImplementation(versionCatalogAlias: String) {
    val configurationName = object {}.javaClass.enclosingMethod.name
    add(configurationName, libs.findLibrary(versionCatalogAlias).get())
}

context(Project)
fun DependencyHandlerScope.androidTestImplementation(versionCatalogAlias: String) {
    val configurationName = object {}.javaClass.enclosingMethod.name
    add(configurationName, libs.findLibrary(versionCatalogAlias).get())
}



