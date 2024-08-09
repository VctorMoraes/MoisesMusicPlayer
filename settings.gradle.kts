pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Moises MusicPlayer"
include(":app")
include(":core:common:composables")
include(":core:common:exceptions")
include(":core:common:strings")
include(":core:model")
include(":core:network")
include(":data:media-player-service")
include(":data:song-search")
include(":domain:album-search")
include(":domain:song-search")
include(":feature:song-player")
include(":feature:song-search")
