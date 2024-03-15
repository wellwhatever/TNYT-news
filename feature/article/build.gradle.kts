plugins {
    alias(libs.plugins.news.android.library)
    alias(libs.plugins.news.android.library.compose)
    alias(libs.plugins.news.android.compose.navigation)
}

dependencies {
}

android {
    namespace = "com.example.news.feature.article"
}