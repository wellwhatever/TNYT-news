plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
    compileOnly(libs.firebase.appdistribution.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.sqldelight.gradlePlugin)
}

group = "com.example.news.build-logic"

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "news.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "news.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "news.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "news.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidTest") {
            id = "news.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("androidFirebase") {
            id = "news.android.firebase"
            implementationClass = "AndroidApplicationFirebaseConventionPlugin"
        }
        register("androidNavigation") {
            id = "news.android.navigation"
            implementationClass = "AndroidComposeNavigationConventionPlugin"
        }
        register("kotlinMultiplatform") {
            id = "news.kotlin.multiplatform.library"
            implementationClass = "KotlinMultiplatformLibraryConventionPlugin"
        }
        register("sqldelight") {
            id = "news.database.sqldelight"
            implementationClass = "SqlDelightConventionPlugin"
        }
        register("kmmUnitTest") {
            id = "news.kotlin.multiplatform.test"
            implementationClass = "KotlinMultiplatformAndroidLibraryTestConvention"
        }
    }
}
