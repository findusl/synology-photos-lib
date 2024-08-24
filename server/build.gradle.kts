plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
}

group = "de.lehrbaum.lib.synology.photos"
version = "1.0.0"
application {
    mainClass.set("de.lehrbaum.lib.synology.photos.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    
    implementation(libs.logback)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.jetty)
    implementation(libs.ktor.client.logging)
    implementation("ch.qos.logback:logback-classic:+")
    testImplementation(libs.kotlin.test.junit)
}