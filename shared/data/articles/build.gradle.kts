plugins {
    alias(libs.plugins.news.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.serialization)
    id("news.kotlin.multiplatform.test")
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

        androidUnitTest.dependencies {
            implementation(libs.sqldelight.driver)
            implementation(libs.ktor.test)
            implementation(projects.shared.core.testing)
        }
    }
}

android {
    namespace = "com.example.news.shared.data.articles"
}
