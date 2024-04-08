plugins {
    alias(libs.plugins.news.android.application)
    alias(libs.plugins.news.android.application.compose)
    alias(libs.plugins.news.android.compose.navigation)
    alias(libs.plugins.news.android.firebase)
}

android {
    namespace = "com.example.news.android"
    defaultConfig {
        applicationId = "com.example.news.android"
        versionCode = 1
        versionName = "1.0"
    }
    signingConfigs {
        create("release") {
            // TODO extract to secrets
            keyAlias = "key0"
            keyPassword = "password"
            storeFile = File("$rootDir/keys/keystore.jks")
            storePassword = "password"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.bundles.compose)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.timber)

    debugImplementation(libs.compose.uiTooling)

    implementation(projects.feature.article)
    implementation(projects.shared.domain.articles)
}