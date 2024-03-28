plugins {
    alias(libs.plugins.news.kotlin.multiplatform.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlin.datetime)
        }
    }
}

android {
    namespace = "com.example.news.shared.core.model"
}
