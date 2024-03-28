import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

plugins {
    with(libs.plugins) {
        listOf(
            android.application,
            android.library,
            kotlin.android,
            kotlin.parcelize,
            android.gms,
            kotlin.ksp,
            kotlin.serialization,
            sqldelight,
            skie,
            firebase.crashlytics,
            safeargs
        ).forEach {
            alias(it) apply false
        }
        alias(detekt)
    }
}

val reportMerge by tasks.registering(ReportMergeTask::class) {
    output.set(rootProject.layout.buildDirectory.file("reports/detekt/merge.sarif"))
}

subprojects {
    apply(plugin = rootProject.libs.plugins.detekt.get().pluginId)

    dependencies {
        detektPlugins(rootProject.libs.detekt.formatting)
        detektPlugins(rootProject.libs.detekt.compose)
    }

    tasks.withType<Detekt>().configureEach {
        autoCorrect = true

        exclude("**/resources/**")
        exclude("**/generated/**")
        exclude("**/iosApp/**")
        exclude("**/build/**")

        if (File("$projectDir/detekt-baseline.xml").exists()) {
            baseline = file("$projectDir/detekt-baseline.xml")
        }
        finalizedBy(reportMerge)
    }

    reportMerge {
        input.from(tasks.withType<Detekt>().map { it.sarifReportFile })
    }
}

tasks.register("detektAll") {
    allprojects {
        this@register.dependsOn(tasks.withType<Detekt>())
    }
}