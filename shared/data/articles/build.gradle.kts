plugins {
    alias(libs.plugins.news.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlin.serialization)
            implementation(libs.kotlin.datetime)
            implementation(libs.ktor.core)
            implementation(libs.koin.multiplatform)
            implementation(libs.sqldelight.coroutines)

            implementation(projects.shared.core.network)
            implementation(projects.shared.core.common)
            implementation(projects.shared.core.model)
            implementation(projects.shared.database)
        }
    }
}

android {
    namespace = "com.example.news.shared.data.articles"
}
