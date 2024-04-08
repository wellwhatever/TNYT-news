import com.example.news.versionCatalog
import com.google.firebase.appdistribution.gradle.AppDistributionExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationFirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = versionCatalog()
            with(pluginManager) {
                apply("com.google.gms.google-services")
                apply("com.google.firebase.crashlytics")
                apply("com.google.firebase.appdistribution")
            }

            dependencies {
                val bom = libs.findLibrary("firebase-bom").get()
                add("implementation", platform(bom))
                "implementation"(libs.findLibrary("firebase-analytics").get())
                "implementation"(libs.findLibrary("firebase-crashlytics").get())
            }

            extensions.configure<AppDistributionExtension> {
                serviceCredentialsFile =
                    "${rootProject.projectDir}/tnytnews-firebase-adminsdk-5tj17-8f449b669c.json"
                artifactType = "APK"
                group = "testers"
            }
        }
    }
}
