package com.example.news

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun Project.configureAndroid(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    val libs = versionCatalog()
    commonExtension.apply {
        compileSdk = libs.findVersion("compileSdk").get().toString().toInt()
        defaultConfig {
            minSdk = libs.findVersion("minSdk").get().toString().toInt()
        }
        compileOptions {
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        lint {
            checkDependencies = true
        }
        dependencies {
            add("coreLibraryDesugaring", libs.findLibrary("desugaring").get())
        }
    }
}

fun Project.configureKotlin() {
    // Use withType to workaround https://youtrack.jetbrains.com/issue/KT-55947
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()

            kotlinOptions {
                freeCompilerArgs += listOf(
                    "-Xcontext-receivers",
                    "-Xexpect-actual-classes"
                )
            }
        }
    }
}
