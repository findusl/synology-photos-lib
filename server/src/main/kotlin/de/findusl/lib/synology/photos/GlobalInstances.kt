package de.findusl.lib.synology.photos

import kotlinx.serialization.json.Json

val JsonDeserializer = Json {
	ignoreUnknownKeys = true
	explicitNulls = false
}