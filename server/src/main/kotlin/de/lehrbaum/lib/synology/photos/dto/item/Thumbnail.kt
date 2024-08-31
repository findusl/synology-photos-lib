package de.lehrbaum.lib.synology.photos.dto.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Thumbnail(
    @SerialName("cache_key")
    val cacheKey: String,
	@SerialName("unit_id")
	val unitId: Int,
    @SerialName("m")
    val m: String, // TODO this can probably be an enum of "ready" and "broken"
    @SerialName("preview")
    val preview: String, // same enum
    @SerialName("sm")
    val sm: String, // same enum
    @SerialName("xl")
    val xl: String // same enum
)