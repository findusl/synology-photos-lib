plugins {
	alias(libs.plugins.kotlinJvm)
	alias(libs.plugins.ktor)
	alias(libs.plugins.kotlinx.serialization)
	alias(libs.plugins.test.logger) // nice log output
	application
}

group = "de.findusl.lib.synology.photos"
version = "1.0.0"
application {
	mainClass.set("de.findusl.lib.synology.photos.ApplicationKt")
	applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
	implementation(libs.ktor.client.core)
	implementation(libs.ktor.client.cio)
	implementation(libs.ktor.client.jetty)
	implementation(libs.ktor.client.content.negotiation)
	implementation(libs.ktor.serialization.kotlinx)
	implementation(libs.ktor.client.logging)
	implementation(libs.ktor.server.core)
	implementation(libs.ktor.server.cio)
	implementation(libs.ktor.server.call.logging)
	implementation(libs.ktor.server.content.negotiation)
	implementation(libs.logback)
	implementation(libs.kotlinx.serialization)
	implementation(libs.kotlinx.serialization.json)
	implementation(libs.kotlin.result)
	implementation(libs.kotlin.result.coroutines)

	testImplementation(kotlin("test"))
}

tasks.test {
	useJUnitPlatform()
}
