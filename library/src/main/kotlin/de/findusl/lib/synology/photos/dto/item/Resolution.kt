package de.findusl.lib.synology.photos.dto.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Resolution(
    @SerialName("height")
    val height: Int,
    @SerialName("width")
    val width: Int
)