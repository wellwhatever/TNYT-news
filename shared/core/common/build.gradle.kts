plugins {
    alias(libs.plugins.news.kotlin.multiplatform.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {

        }

        androidMain.dependencies {

        }

        iosMain.dependencies {

        }
    }
}

android {
    namespace = "com.example.news.shared.core.common"
}
