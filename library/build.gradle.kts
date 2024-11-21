plugins {
	alias(libs.plugins.kotlinJvm)
	alias(libs.plugins.ktor)
	alias(libs.plugins.kotlinx.serialization)
	alias(libs.plugins.test.logger) // nice log output
	application
	id("maven-publish")
	id("signing")
}

group = "de.findusl.lib.synology.photos"
version = "1.0.0-alpha"

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

java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}

// Configure publishing details
publishing {
	publications {
		register("mavenJava", MavenPublication::class) {
			from(components["java"])

			groupId = "de.findusl"
			artifactId = "synology-photos-lib"
			version = "1.0.0"

			pom {
				name.set("Synology Photos Library")
				description.set("A library to interact with Synology Photos via their http API.")
				url.set("https://github.com/findusl/synology-photos-lib")

				licenses {
					license {
						name.set("MIT License")
						url.set("https://opensource.org/licenses/MIT")
					}
				}

				developers {
					developer {
						id.set("findusl")
						name.set("Sebastian Lehrbaum")
						email.set("dev@findusl.de")
					}
				}

				scm {
					connection.set("scm:git:git://github.com/findusl/synology-photos-lib.git")
					developerConnection.set("scm:git:ssh://github.com:findusl/synology-photos-lib.git")
					url.set("https://github.com/findusl/synology-photos-lib")
				}
			}
		}
	}

	repositories {
		maven {
			url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
			credentials {
				username = "your-sonatype-username"
				password = "your-sonatype-password"
			}
		}
	}
}
