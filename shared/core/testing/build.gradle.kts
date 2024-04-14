plugins {
    alias(libs.plugins.news.kotlin.multiplatform.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.multiplatform)
            implementation(libs.bundles.ktor.multiplatform)
            implementation(libs.kotlin.coroutinesCore)
            implementation(libs.kotlin.serialization)
            implementation(libs.kotlin.datetime)

            implementation(projects.shared.core.model)
            implementation(projects.shared.core.common)
            implementation(projects.shared.data.articles)
        }

        androidMain.dependencies {
            implementation(libs.ktor.android)
        }

        iosMain.dependencies {
            implementation(libs.ktor.ios)
        }
    }
}

android {
    namespace = "com.example.shared.core.testing"
}