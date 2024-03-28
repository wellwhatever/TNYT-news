import app.cash.sqldelight.gradle.SqlDelightExtension
import com.example.news.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class SqlDelightConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = versionCatalog()

            with(pluginManager) {
                apply("app.cash.sqldelight")
                apply("org.jetbrains.kotlin.multiplatform")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                with(sourceSets) {
                    androidMain.dependencies {
                        implementation(libs.findLibrary("sqldelight-android").get())
                    }
                    commonMain.dependencies {
                        implementation(libs.findLibrary("sqldelight-common").get())
                        implementation(libs.findLibrary("sqldelight-coroutines").get())
                    }
                    iosMain.dependencies {
                        implementation(libs.findLibrary("sqldelight-ios").get())
                    }
                }
            }

            extensions.configure<SqlDelightExtension> {
                databases.create("ArticleDatabase") {
                    packageName.set("com.example.news")
                    generateAsync.set(true)

                }
                linkSqlite.set(true)
            }
        }
    }
}