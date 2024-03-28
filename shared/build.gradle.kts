import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.news.kotlin.multiplatform.library)
}

kotlin {
    sourceSets {
        targets.withType<KotlinNativeTarget> {
            binaries.withType<Framework> {
                baseName = "shared"
                isStatic = true
                export(projects.shared.core.common)
                export(projects.shared.core.model)
                export(projects.shared.core.network)
                export(projects.shared.domain.articles)
            }
        }

        sourceSets {
            commonMain.dependencies {
                api(projects.shared.core.common)
                api(projects.shared.core.model)
                api(projects.shared.core.network)
                api(projects.shared.domain.articles)
            }
        }
    }
}

android {
    namespace = "com.example.news.shared"
}
