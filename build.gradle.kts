import java.util.Properties

plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"

}

group = "dev.blackoutburst.gptest"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {

    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    implementation("net.dv8tion:JDA:5.0.0-beta.21")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(18)
}

sourceSets {
    val main by getting {
        java.srcDir("$buildDir/generated/source/buildConfig")
    }
}

tasks.register("generateBuildConfig") {
    doLast {
        val buildConfigFile = File("$buildDir/generated/source/buildConfig/dev/blackoutburst/gptest/BuildConfig.kt")
        buildConfigFile.parentFile.mkdirs()

        val localProps = Properties()
        file("local.properties").inputStream().use {
            localProps.load(it)
        }

        buildConfigFile.writeText("""
            package dev.blackoutburst.gptest

            object BuildConfig {
                const val VERSION_NAME = "${project.version}"
                const val GROUP = "${project.group}"
                const val DISCORD_TOKEN = "${localProps["discordToken"]}"
                const val OPENAI_KEY = "${localProps["openaiKey"]}"
                const val MISTRAL_KEY = "${localProps["mistralKey"]}"
            }
        """.trimIndent())
    }
}