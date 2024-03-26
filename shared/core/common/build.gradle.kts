plugins {
    alias(libs.plugins.news.kotlin.multiplatform.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.multiplatform)
            implementation(libs.kotlin.coroutinesCore)
            implementation(libs.kotlin.serialization)
            implementation(libs.kotlin.datetime)
        }
    }
}

android {
    namespace = "com.example.news.shared.core.common"
}
