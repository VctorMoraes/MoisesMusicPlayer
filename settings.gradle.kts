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
include(":core:model")
include(":core:network")
include(":data:media-player-service")
include(":data:song-search")
include(":domain")
include(":domain:song-search")
include(":feature:song-player")
include(":feature:song-search")
