plugins {
    alias(libs.plugins.news.android.application)
    alias(libs.plugins.news.android.application.compose)
    alias(libs.plugins.news.android.compose.navigation)
//    alias(libs.plugins.news.android.firebase)
}

android {
    namespace = "com.example.news.android"
    defaultConfig {
        applicationId = "com.example.news.android"
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.bundles.compose)
    implementation(libs.koin.android)
    implementation(libs.timber)

    debugImplementation(libs.compose.uiTooling)

    implementation(projects.feature.article)
    implementation(projects.shared.domain.articles)
}