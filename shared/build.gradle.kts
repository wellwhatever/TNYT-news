plugins {
    alias(libs.plugins.news.kotlin.multiplatform.library)
//    alias(libs.plugins.news.android.library)
//    alias(libs.plugins.news.android.firebase)
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
    namespace = "com.example.news"
}
