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
    namespace = "com.example.news.shared.core"
}
