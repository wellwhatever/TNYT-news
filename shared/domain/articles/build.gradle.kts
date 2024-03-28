plugins {
    alias(libs.plugins.news.kotlin.multiplatform.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.multiplatform)
            implementation(libs.kotlin.coroutinesCore)

            implementation(projects.shared.core.model)
            implementation(projects.shared.core.common)
            implementation(projects.shared.data.articles)
        }
    }
}

android {
    namespace = "com.example.news.shared.domain.articles"
}
