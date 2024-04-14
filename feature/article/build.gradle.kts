plugins {
    alias(libs.plugins.news.android.library)
    alias(libs.plugins.news.android.library.compose)
    alias(libs.plugins.news.android.compose.navigation)
    alias(libs.plugins.news.android.test)
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
    implementation(projects.shared.core.testing)
    implementation(projects.shared.domain.articles)

    testImplementation(libs.bundles.unitTesting)
}

android {
    namespace = "com.example.news.feature.article"
}