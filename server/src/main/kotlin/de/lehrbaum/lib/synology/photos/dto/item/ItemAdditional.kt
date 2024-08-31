package de.lehrbaum.lib.synology.photos.dto.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemAdditional(
    @SerialName("address")
    val address: Address?,
    @SerialName("orientation")
    val orientation: Int?,
    @SerialName("orientation_original")
    val orientationOriginal: Int?,
    @SerialName("resolution")
    val resolution: Resolution?,
    @SerialName("thumbnail")
    val thumbnail: Thumbnail?,
)