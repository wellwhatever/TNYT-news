enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {

    repositories {
        includeBuild("build-logic")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TNYT_News"
include(":androidApp")
include(":shared")
include(":shared:core:network")
include(":shared:core:common")
include(":shared:data:articles")
include(":shared:domain:articles")
