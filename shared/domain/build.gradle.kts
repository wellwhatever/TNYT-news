plugins {
    alias(libs.plugins.news.kotlin.multiplatform.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.core.model)
        }
    }
}

android {
    namespace = "com.example.news.shared.domain"
}
