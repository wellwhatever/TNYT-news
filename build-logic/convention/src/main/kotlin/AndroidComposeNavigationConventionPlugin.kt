import com.example.news.versionCatalog
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidComposeNavigationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("androidx.navigation.safeargs.kotlin")
            }

            val libs = versionCatalog()
            dependencies {
                "implementation"(libs.findBundle("navigation").get())
                "ksp"(libs.findLibrary("compose-destinations-ksp").get())
            }

            extensions.configure<KspExtension> {
                arg("compose-destinations.mode", "navgraphs")
                arg("compose-destinations.moduleName", target.name)
            }
        }
    }
}
