import com.android.build.gradle.LibraryExtension
import com.example.news.configureAndroid
import com.example.news.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

class KotlinMultiplatformLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
            }
            extensions.configure<LibraryExtension> {
                buildFeatures {
                    buildConfig = true
                }
                configureAndroid(this)
                configureKotlin()
                defaultConfig {
                    consumerProguardFiles("consumer-rules.pro")
                }
            }
            extensions.configure<KotlinMultiplatformExtension> {
                if (pluginManager.hasPlugin("com.android.library")) {
                    androidTarget()
                }

                listOf(
                    iosX64(),
                    iosArm64(),
                    iosSimulatorArm64()
                ).forEach {
                    it.binaries.framework {
                        baseName = "shared"
                        isStatic = true
                    }
                }
            }
        }
    }
}
