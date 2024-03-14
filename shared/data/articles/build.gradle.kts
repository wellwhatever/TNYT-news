plugins {
    alias(libs.plugins.news.kotlin.multiplatform.library)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {

            }
        }
        val commonTest by getting {
            dependencies {
                // test dependencies
            }
        }
    }
}

android {
    namespace = "com.example.news.shared.data"
}
