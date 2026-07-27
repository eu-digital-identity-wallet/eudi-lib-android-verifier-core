/*
 * Copyright (c) 2025 European Commission
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.github.jk1.license.filter.ExcludeTransitiveDependenciesFilter
import com.github.jk1.license.filter.LicenseBundleNormalizer
import com.github.jk1.license.filter.ReduceDuplicateLicensesFilter
import com.github.jk1.license.render.InventoryMarkdownReportRenderer
import com.vanniktech.maven.publish.AndroidMultiVariantLibrary
import org.jetbrains.dokka.gradle.formats.DokkaFormatPlugin
import org.jetbrains.dokka.gradle.internal.InternalDokkaGradlePluginApi

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.dokka)
    alias(libs.plugins.dependency.license.report)
    alias(libs.plugins.dependencycheck)
    alias(libs.plugins.sonarqube)
}

val NAMESPACE: String by project
val GROUP: String by project
val POM_SCM_URL: String by project
val POM_DESCRIPTION: String by project


// handle java.lang.UnsupportedOperationException: PermittedSubclasses requires ASM9
// when publishing module
afterEvaluate {
    tasks.matching { it.name == "javaDocReleaseGeneration" }.configureEach {
        enabled = false
    }
}

android {
    namespace = NAMESPACE
    group = GROUP
    compileSdk = 36

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testApplicationId = "$NAMESPACE.test"
        testHandleProfiling = true
        testFunctionalTest = true

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = false
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.java.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.java.get())
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }

    packaging {
        resources {
            excludes += listOf("/META-INF/{AL2.0,LGPL2.1}")
            excludes += listOf("/META-INF/versions/9/OSGI-INF/MANIFEST.MF")
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.fromTarget(libs.versions.java.get()))
        freeCompilerArgs.add("-opt-in=kotlin.time.ExperimentalTime")
    }
}

dependencies {
    implementation(libs.com.upokecenter.cbor)
    api(libs.multipaz.android)
    implementation(libs.multipaz.android.legacy)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    // Document status
    api(libs.eudi.lib.kmp.statium)
    implementation(libs.nimbus.jose)
    runtimeOnly(libs.ktor.client.android)
    implementation(libs.ktor.client.logging)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    testImplementation(kotlin("test-junit"))
    testImplementation(libs.mockk)
    testImplementation(libs.json)
    testImplementation(libs.kotlin.coroutines.test)
}

// Dokka 2.x — apply GFM (markdown) format plugin and configure output per module
@OptIn(InternalDokkaGradlePluginApi::class)
abstract class DokkaMarkdownPlugin : DokkaFormatPlugin(formatName = "markdown") {
    override fun DokkaFormatPlugin.DokkaFormatPluginContext.configure() {
        project.dependencies {
            dokkaPlugin(dokka("gfm-plugin"))
            formatDependencies.dokkaPublicationPluginClasspathApiOnly
                .dependencies.addLater(dokka("gfm-template-processing-plugin"))
        }
    }
}
apply<DokkaMarkdownPlugin>()

dokka {
    moduleName.set(project.name)
    dokkaPublications.named("markdown") {
        outputDirectory.set(file("$projectDir/docs"))
    }
}

tasks.named("dokkaGeneratePublicationMarkdown").configure {
    doFirst { delete(file("$projectDir/docs")) }
}

tasks.named("dokkaGenerateMarkdown") {
    group = "documentation"
}

tasks.register<Jar>("dokkaHtmlJar") {
    group = "documentation"
    dependsOn("dokkaGeneratePublicationHtml")
    from(tasks.named("dokkaGeneratePublicationHtml"))
    archiveClassifier.set("html-docs")
    description = "Assembles a JAR containing the HTML documentation generated by Dokka."
}

// Replace the javadoc.jar contents with Dokka's HTML output (AGP's javaDocReleaseGeneration is
// disabled above, as it hits a "PermittedSubclasses requires ASM9" error on this codebase).
afterEvaluate {
    tasks.findByName("javaDocReleaseJar")?.let { task ->
        if (task is Jar) {
            task.from(tasks.named("dokkaGeneratePublicationHtml"))
            task.dependsOn("dokkaGeneratePublicationHtml")
        }
    }
}

// Third-party licenses report

licenseReport {
    unionParentPomLicenses = false
    filters = arrayOf(
        LicenseBundleNormalizer(),
        ReduceDuplicateLicensesFilter(),
        ExcludeTransitiveDependenciesFilter()
    )
    configurations = arrayOf("releaseRuntimeClasspath")
    excludeBoms = true
    excludeOwnGroup = true
    renderers = arrayOf(InventoryMarkdownReportRenderer("licenses.md", POM_DESCRIPTION))
}

tasks.generateLicenseReport.configure {
    doLast {
        copy {
            from(layout.buildDirectory.file("reports/dependency-license/licenses.md"))
            into(rootDir)
        }
    }
}

// Build documentation and license report
tasks.register<Task>("buildDocumentation") {
    group = "documentation"
    dependsOn("dokkaGenerateMarkdown", "generateLicenseReport")
}
tasks.assemble.configure {
    finalizedBy("buildDocumentation")
}

// Publish

mavenPublishing {
    configure(
        AndroidMultiVariantLibrary(
            sourcesJar = true,
            publishJavadocJar = true,
            setOf("release")
        )
    )
    pom {
        ciManagement {
            system = "github"
            url = "${POM_SCM_URL}/actions"
        }
    }
}