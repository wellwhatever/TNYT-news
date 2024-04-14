import com.android.build.gradle.LibraryExtension
import com.example.news.configureAndroid
import com.example.news.configureKotlin
import com.example.news.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformAndroidLibraryTestConvention : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = versionCatalog()
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
            }
            extensions.configure<LibraryExtension> {
                configureAndroid(this)
                configureKotlin()
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }
            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.androidMain.dependencies {
                    implementation(libs.findBundle("kmm-unit-testing").get())
                }
            }
            tasks.withType<Test>().configureEach {
                useJUnitPlatform()
            }
        }
    }
}