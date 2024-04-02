import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import com.example.news.versionCatalog
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
            }

            dependencies {
                val bom = libs.findLibrary("firebase-bom").get()
                add("implementation", platform(bom))
                "implementation"(libs.findLibrary("firebase-analytics").get())
                "implementation"(libs.findLibrary("firebase-crashlytics").get())
            }
        }
    }
}
