plugins {
    alias(libs.plugins.news.kotlin.multiplatform.library)

    // TODO check out why this plugin cannot be resolved!!!
//    alias(libs.plugins.news.database.sqldelight)
    id("news.database.sqldelight")

}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.multiplatform)
            implementation(libs.kotlin.coroutinesCore)
            implementation(libs.kotlin.serialization)

            implementation(projects.shared.core.model)
            implementation(projects.shared.core.common)
        }
    }
}

android {
    namespace = "com.example.news.database"
}
