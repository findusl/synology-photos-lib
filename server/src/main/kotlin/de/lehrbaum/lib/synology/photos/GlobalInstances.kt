package de.lehrbaum.lib.synology.photos

import kotlinx.serialization.json.Json

val JsonDeserializer = Json {
    ignoreUnknownKeys = true
}