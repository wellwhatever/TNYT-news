plugins {
    alias(libs.plugins.news.android.library)
    alias(libs.plugins.news.android.library.compose)
    alias(libs.plugins.news.android.compose.navigation)
}

dependencies {
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.compose.material)
    implementation(libs.kotlin.datetime)
    implementation(libs.timber)

    implementation(projects.shared.core.model)
    implementation(projects.shared.core.common)
    implementation(projects.shared.core.network)
    implementation(projects.shared.domain.articles)
}

android {
    namespace = "com.example.news.feature.article"
}