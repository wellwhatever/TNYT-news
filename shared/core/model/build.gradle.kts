plugins {
    alias(libs.plugins.news.kotlin.multiplatform.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlin.datetime)
            implementation(libs.kotlin.coroutinesCore)
        }
    }
}

android {
    namespace = "com.example.news.shared.core.model"
}
