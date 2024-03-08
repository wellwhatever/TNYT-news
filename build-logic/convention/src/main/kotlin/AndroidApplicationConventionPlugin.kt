import com.android.build.api.dsl.ApplicationExtension
import com.example.news.configureAndroid
import com.example.news.configureKotlin
import com.example.news.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<ApplicationExtension> {
                buildFeatures {
                    buildConfig = true
                }
                configureAndroid(this)
                configureKotlin()
                defaultConfig.targetSdk =
                    versionCatalog().findVersion("targetSdk").get().toString().toInt()
                buildTypes {
                    release {
                        isMinifyEnabled = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro",
                        )
                    }
                }
            }
        }
    }
}
